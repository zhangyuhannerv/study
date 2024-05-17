package com.study.gulimall.product.web;

import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.Catalog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        } catch (Exception e) {
            log.error("业务执行失败，{}", e.getMessage());
        } finally {
            // 解锁
            log.info("释放锁");
            lock.unlock();
        }
        return "hello";
    }
}
