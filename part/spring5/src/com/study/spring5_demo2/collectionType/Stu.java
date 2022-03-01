package com.study.spring5_demo2.collectionType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Stu
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/2/28
 * @Version 1.0
 */
public class Stu {
    // 数组类型属性
    private String[] courses;

    // list集合类型属性
    private List<String> list;

    // map集合类的属性
    private Map<String, String> map;

    // set集合类型的属性
    private Set<String> set;

    // 学生所学的多门课程
    private List<Course> courseList;

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "courses=" + Arrays.toString(courses) +
                ", list=" + list +
                ", map=" + map +
                ", set=" + set +
                ", courseList=" + courseList +
                '}';
    }
}
