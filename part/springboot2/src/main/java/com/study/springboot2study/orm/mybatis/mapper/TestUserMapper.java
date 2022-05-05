package com.study.springboot2study.orm.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.springboot2study.bean.TestUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
// 注意，如果不想写@Mapper可以在任意一个配置类上写上@MapperScan注解（详见主运行类）
// 但是不推荐使用@MapperScan注解，推荐使用@Mapper注解，便于分层
public interface TestUserMapper extends BaseMapper<TestUser> {
    TestUser getById(int id);

    /**
     * 使用这种方式不需要任何方式mapper.xml。之前删掉了全局配置，现在又删掉了sql映射文件，所以现在是纯注解的方式
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    TestUser getById2(int id);

    void insertTestUser(TestUser testUser);

    /**
     * 上面insert的纯注解方式的写法
     *
     * @param testUser
     */
    @Insert("insert into user(`name`,`typeid`) values(#{name},#{typeid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTestUser2(TestUser testUser);

    // 综上，可以看出，所有的sql映射文件都可以用纯注解的方式写出
}
