package com.study.gulimall.product.web;

import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.Catalog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class IndexController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // 1.查出所有的一级分类
        List<CategoryEntity> list = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", list);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }

    @GetMapping("/testRedissonLock")
    @ResponseBody
    public String testRedissonLock() {
        // 获取一把锁，只要锁的名字一样，就是同一把锁
        String lockName = UUID.randomUUID().toString();
        RLock lock = redissonClient.getLock(lockName);
        // 加锁
        // 这是一个阻塞锁，没有获取到锁就一直等，和本地锁（synchronized）一样，但是和之前用原生redis写的锁不一样，那个是自旋锁，不断重试
        // redisson的锁的默认时间是30s
        // 加锁后，如果程序闪断，没有执行解锁代码，那么30s后锁就到期，此时自动解锁
        // 并且redisson利用了redis的看门狗，解决了锁的自动续期功能，如果业务执行时间超长，会自动给锁再续上30s
        // 不用担心业务时间长导致锁自动过期被删掉
        // 如果未指定锁的超时时间，就使用30*1000（30s,LockWatchdogTimeout）作为锁的默认时间，也是看门狗的默认时间
        // 同时LockWatchdogTimeout/3即10s作为锁的续期时间，业务没有执行完，锁没有解除占用，每过10s都会重新给锁续到30s
//        lock.lock();

        // 如果能加锁成功，那么10s后自动解锁
        // 这种方式上锁，不会自动续期
        // 时间一到，如果业务没有执行完，执行解锁代码，redis就会删除锁。此时当业务执行完想解锁时，没有属于自己的锁，解不了，会抛出异常
        // 因此用这种锁一定要设置自动解锁的时间大于业务执行的时间
        // 如果传递了锁的超时时间，就发送给redis执行脚本，进行占锁，默认超时就是我们指定的时间
//        lock.lock(10, TimeUnit.SECONDS);


        // 最佳实战,实战用下面这个
        // 设置一个业务可能执行的最大时间。同时省掉了锁的续期操作
        // 业务如果正常执行完，手动解锁
        lock.lock(30, TimeUnit.SECONDS);

        try {
            log.info("加锁成功，执行业务。线程id:{}", Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(30);
            return "hello";
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 解锁
            log.info("释放锁");
            lock.unlock();
        }
    }

    /**
     * 测试读写锁
     * 读+读，相当于无锁，并发读，会同时加锁成功，只会在redis记录好各个读锁，无其他作用。
     * 写+读，读锁等待写锁释放
     * 写+写，阻塞方式，下一个写锁必须等待上一个写锁释放
     * 读+写，有读锁，写也需要等待，等待读锁释放后才能写
     * 只要有写的存在，无论是先写，后写，还是写写，都必须等待
     *
     * @return
     */
    @RequestMapping("/write")
    @ResponseBody
    public String write() {
        // 读写锁的用法
        // 改数据加写锁
        // 读数据加读锁
        // 保证一定能读到最新数据。
        // 修改期间，写锁是一个排他锁（互斥锁），相同的写锁只能存在一个，如果有多个请求都要加同一个写锁，那么就得排好队竞争
        // 读锁是一个共享锁。在没有写锁存在的情况下，和没加锁效果是一样的,多个线程能同时读到。但是一旦要有写锁，多个线程就会阻塞，直到等到写锁解锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        // 获取写锁
        RLock rLock = lock.writeLock();
        String key = "writeValue";
        try {
            // 加写锁
            rLock.lock();
            String uuid = UUID.randomUUID().toString();
            TimeUnit.SECONDS.sleep(30);
            redisTemplate.opsForValue().set(key, uuid);
            return uuid;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
    }

    @RequestMapping("/read")
    @ResponseBody
    public String read() {
        // 读写锁是同一把锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        // 获取读锁
        RLock rLock = lock.readLock();
        try {
            // 加读锁
            rLock.lock();
            String key = "writeValue";
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }

    }

    /**
     * 测试信号量
     * 以车库停车为例子
     * 3车位
     * 来一辆占一个，走了一个那就释放一个
     * redis设置key为park，值为3作为测试
     * 类似这种场景就可以用信号量
     */
    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        // 同一个名字就对应同一个分布式信号量
        RSemaphore park = redissonClient.getSemaphore("park");
        // 获取一个信号，可以理解为获取一个值(占一个车位）
        // 只有获取到才能向下走，返回ok
        // 获取不到就会一直阻塞等待,直到获取成功
        // redis设置key为park，值为3作为测试。每次acquire()，都使park-1，park为0后再acquire()就会阻塞等待，直到release()使park+1
        park.acquire();
        return "ok";


        // 非阻塞式等待，可以用来给接口限流
//        boolean b = park.tryAcquire();
//        if (b) {
//            // 执行业务
//            //...
//            // 业务执行完毕，释放
//            park.release();
//        } else {
//            return "系统繁忙";
//        }
    }

    @GetMapping("/go")
    @ResponseBody
    public String go() throws InterruptedException {
        // 同一个名字就对应同一个分布式信号量
        RSemaphore park = redissonClient.getSemaphore("park");
        park.release();// 释放一个车位
        return "ok";
    }

    /**
     * 闭锁
     * 以放假锁门为例
     * 有5个班，5个班的人都走了，门卫才能锁门
     * redis里设置一个key为door，value为5的数据作为测试
     */
    @GetMapping("/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.await();// 等待闭锁都完成
        return "都走了，锁门";
    }

    @GetMapping("/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable Long id) {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        // redis里设置一个key为door，value为5的数据作为测试
        door.countDown();
        return id + "班的人都走了...";
    }
}
