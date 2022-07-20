package com.study.srb_mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.study.srb_mybatis_plus.entity.User;
import com.study.srb_mybatis_plus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTests {
    @Resource
    UserMapper userMapper;

    /**
     * 查询名字中包含n，年龄大于等于10且小于等于20，email不为空的用户
     */
    @Test
    public void test1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // column:对应数据库表中的列名
        queryWrapper
                .like("username", "1")
                .between("age", 50, 60)
                .isNotNull("email");

        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    /**
     * 按年龄降序查询用户，如果年龄相同则按id升序排列
     */
    @Test
    public void test2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    /**
     * 删除email为空的用户
     */
    @Test
    public void Test3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("删除的记录数：" + result);
    }

    /**
     * 条件的优先级
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@qq.com
     */
    @Test
    public void test4() {
        // 组装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("username", "n")
                .and(e -> e.lt("age", 18).or().isNull("email"));
        // 组装更新条件
        User user = new User();
        user.setAge(18);
        user.setEmail("user@qq.com");
        // 执行更新
        int result = userMapper.update(user, queryWrapper);
        System.out.println("更新结果：" + result);
    }

    /**
     * 组装select子句
     * 查询所有用户的用户名和年龄
     */
    @Test
    public void test5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // select()通常和selectMaps()搭配
        queryWrapper.select("username", "age");
        // 此时查询出的结果有其他的属性，并且为null值
//        userMapper.selectList(queryWrapper).forEach(System.out::println);
        // 此时查询出来的结果是一个键值对。只包含你要查询的列
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }


    /**
     * 实现子查询(不推荐QueryWrapper,推荐手写sql)
     * 查询id不大于3的所有用户的id列表
     */
    @Test
    public void test6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 第一种子查询方式
//        queryWrapper.inSql("uid", "select uid from t_user where uid <= 3");

        // 第二种子查询方式
        queryWrapper.in("uid", 1, 2, 3);

        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    /**
     * updateWrapper的使用
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@qq.com
     */
    @Test
    public void test7() {
        // 同时组装更新条件和查询条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age", 18)
                .set("email", "user@qq.com")
                .like("username", "n")
                .and(e -> e.lt("age", 18).or().isNull("email"));

        // 注意当entity是null时，自动填充失效
//        int result = userMapper.update(null, updateWrapper);
        // 传进去一个空的实体。测试会检测哪些字段需要自动填充。自动填充生效
        int result = userMapper.update(new User(), updateWrapper);
        System.out.println("更新结果:" + result);
    }

    /**
     * condition的使用场景
     * 查询名字中包含n，年龄大于10且小于20的用户，查询条件来源于用户输入，是可选的
     */
    @Test
    public void test8() {
        // 模拟前台用户输入
        String userName = null;
        Integer ageStart = null;
        Integer ageEnd = 20;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 传统的方式
//        if (StringUtils.isNotBlank(userName)) {
//            queryWrapper.like("username", userName);
//        }
//
//        if (ageStart != null) {
//            queryWrapper.ge("age", ageStart);
//        }
//
//        if (ageEnd != null) {
//            queryWrapper.le("age", ageEnd);
//        }

        // condition方式
        queryWrapper
                .like(StringUtils.isNotBlank(userName), "username", userName)
                .ge(ageStart != null, "age", ageStart)
                .le(ageEnd != null, "age", ageEnd);

        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    /**
     * lambda(需求同test8)
     */
    @Test
    public void test9() {
        // 模拟前台用户输入
        String userName = "a";
        Integer ageStart = null;
        Integer ageEnd = 20;

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                // User::getName实际上是获得name属性所对应的表的列名（比如这里就是获得注解@TabelFile的值username)
                .like(StringUtils.isNotBlank(userName), User::getName, userName)
                .ge(ageStart != null, User::getAge, ageStart)
                .le(ageEnd != null, User::getAge, ageEnd);

        userMapper.selectList(lambdaQueryWrapper).forEach(System.out::println);
    }

    /**
     * lambda(需求同test7）
     */
    @Test
    public void test10() {
        // 同时组装更新条件和查询条件
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAge, 18)
                .set(User::getEmail, "user@qq.com")
                .like(User::getName, "n")
                .and(e -> e.lt(User::getAge, 18).or().isNull(User::getEmail));


        int result = userMapper.update(new User(), updateWrapper);
        System.out.println("更新结果:" + result);
    }
}
