package com.study.srb_mybatis_plus;

import com.study.srb_mybatis_plus.entity.User;
import com.study.srb_mybatis_plus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MapperTests {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("观海1000");
        user.setAge(1000);
        user.setEmail("jianguo@qq.com");
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());
        int res = userMapper.insert(user);
        System.out.println("结果：" + res);
    }

    @Test
    void testSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);

        Map<String, Object> map = new HashMap<>();
        map.put("username", "建国");
        map.put("age", 74);
        List<User> users1 = userMapper.selectByMap(map);
        System.out.println(users1);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(1549222236689387521L);
        user.setAge(201);
        // 更新这里是动态sql，如果对象的某个属性为null，那么就不会更新该字段。
        // email和name都没有更新
        int res = userMapper.updateById(user);
        System.out.println("结果：" + res);
    }

    @Test
    void testDelete() {
        int result = userMapper.deleteById(2L);
        System.out.println("结果：" + result);
    }

    @Test
    void testSelectAllByName() {
        List<User> users = userMapper.selectAllByName("Jack");
        users.forEach(System.out::println);
    }
}
