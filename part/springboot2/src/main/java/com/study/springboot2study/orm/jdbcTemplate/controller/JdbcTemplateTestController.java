package com.study.springboot2study.orm.jdbcTemplate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;

/**
 * @ClassName JDBcTemplateTestController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/4
 * @Version 1.0
 */
@Slf4j
@Controller
public class JdbcTemplateTestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @RequestMapping("/userCount")
    @ResponseBody
    public Integer userCount() {
        log.info("数据源类型：{}", dataSource.getClass());
        return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }
}
