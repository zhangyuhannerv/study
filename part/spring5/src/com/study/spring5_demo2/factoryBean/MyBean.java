package com.study.spring5_demo2.factoryBean;

import com.study.spring5_demo2.collectionType.Course;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName MyBean
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/1
 * @Version 1.0
 */
public class MyBean implements FactoryBean<Course> {

    /**
     * 主要在这里定义返回bean
     *
     * @return
     * @throws Exception
     */
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setName("abc");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
