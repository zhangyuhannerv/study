package com.zh.generateCodeByMybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @ClassName Run
 * @Description 粗略的mybatis代码生成器，主要用来生成四层基础结构
 * @Author Zhangyuhan
 * @Date 2022/4/8
 * @Version 1.0
 */
public class Run {
    public static void main(String[] args) {
        String url = "jdbc:mysql://123.123.122.138:3306/djxt_test";
        String user = "djxt_test";
        String pwd = "0f0Ftzh8";
        String generateCodePath = "D:\\javaTest\\generateCode";
        String generateMapperPath = "D:\\javaTest\\generateMapper";

        FastAutoGenerator.create(url, user, pwd)
                .globalConfig(builder -> {
                    builder.author("Zhangyuhan") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(generateCodePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.jxdinfo.hussar.example") // 设置父包名
                            .moduleName("engineeringVehicles"); // 设置父包模块名
                    // .pathInfo(Collections.singletonMap(OutputFile.mapperXml, generateMapperPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("djxt_engineering_car") // 设置需要生成的表名
                            .addTablePrefix("djxt_"); // 设置过滤表前缀
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
