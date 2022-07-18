package com.study.srb_mybatis_plus;

import com.study.srb_mybatis_plus.entity.User;
import com.study.srb_mybatis_plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServiceTests {
    @Resource
    private UserService userService;

    @Test
    public void testCount() {
        int count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    public void testSaveBatch() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("花花" + i);
            user.setAge(20 + i);
            list.add(user);
        }

        boolean res = userService.saveBatch(list);
        System.out.println("结果是" + res);
    }
}
