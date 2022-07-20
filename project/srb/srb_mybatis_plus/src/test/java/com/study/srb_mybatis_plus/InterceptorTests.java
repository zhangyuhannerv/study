package com.study.srb_mybatis_plus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.srb_mybatis_plus.entity.Product;
import com.study.srb_mybatis_plus.entity.User;
import com.study.srb_mybatis_plus.mapper.ProductMapper;
import com.study.srb_mybatis_plus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 插件测试。主要是分页插件测试
 */
@SpringBootTest
public class InterceptorTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Test
    public void testSelectPage() {
        // 查询第二页，每页5条记录
        Page<User> pageParam = new Page<>(6, 5);
        userMapper.selectPage(pageParam, null);
        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);

        long total = pageParam.getTotal();
        System.out.println("一共有" + total + "条记录");

        boolean bn = pageParam.hasNext();
        System.out.println("是否有下一页" + bn);

        boolean bp = pageParam.hasPrevious();
        System.out.println("是否有上一页" + bp);
    }

    @Test
    public void testSelectPageByAge() {
        Page<User> userPage = new Page<>(1, 5);
        userMapper.selectPageByPage(userPage, 18);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }

    /**
     * 演示乐观锁的使用场景
     */
    @Test
    public void testConcurrentUpdate() {
        // 小李取数据
        Product p1 = productMapper.selectById(1L);
        // 小王取数据
        Product p2 = productMapper.selectById(1L);
        // 小李修改数据 + 50
        p1.setPrice(p1.getPrice() + 50);
        int res1 = productMapper.updateById(p1);
        System.out.println("小李修改的结果：" + res1);
        // 小王修改数据 - 30
        p2.setPrice(p2.getPrice() - 30);
        int res2 = productMapper.updateById(p2);
        System.out.println("小王修改的结果：" + res2);
        // 小王修改失败
        if (res2 == 0) {
            // 失败重试
            p2 = productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);
            res2 = productMapper.updateById(p2);
            System.out.println("小王重试的结果：" + res2);
        }

        // 老板看价格
        Product p3 = productMapper.selectById(1L);
        System.out.println("老板看价格：" + p3.getPrice());
    }
}
