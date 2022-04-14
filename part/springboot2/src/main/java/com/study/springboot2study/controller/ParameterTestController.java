package com.study.springboot2study.controller;

import com.study.springboot2study.pojo.Person;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ParameterTestController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/2
 * @Version 1.0
 */

@RestController
public class ParameterTestController {

    // /car/1/owner/zhangsan
    // @PathVariable除了单独提取路径变量外还能将所有的路径变量整合成一个Map<String,String>对象
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(@PathVariable("id") Integer id,
                                      @PathVariable("username") String name,
                                      // 所有的路径变量封装成一个Map
                                      @PathVariable Map<String, String> pv,
                                      // 获取单独的请求头里的某个参数
                                      @RequestHeader("User-Agent") String userAgent,
                                      // 请求头里的所有数据
                                      @RequestHeader Map<String, String> headers,
                                      // 获取单个请求参数
                                      @RequestParam("age") Integer age,
                                      // 获取集合请求参数
                                      @RequestParam("inters") List<String> inters,
                                      // 获取所有的请求参数(注意，这里的params，前台如果是个数组（如inters)那么这里只有一个值，可以自己打断点看下）
                                      @RequestParam Map<String, String> params,
                                      // Cookie有多个，这里要获取单独的某个cookie值
                                      @CookieValue("Webstorm-25a9136") String singleCookieValue,
                                      // 这里获取某个单独的Cookie的所有属性，上面只是获取某个单独的Cookie的值
                                      @CookieValue("Webstorm-25a9136") Cookie cookie
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("pv", pv);
        map.put("userAgent", userAgent);
        map.put("headers", headers);
        map.put("age", age);
        map.put("inters", inters);
        map.put("params", params);
        map.put("singleCookieValue", singleCookieValue);
        map.put("cookie", cookie);
        return map;
    }


    @PostMapping("/save")
    public Map<String, Object> postMethod(
            // 如果用string接收，那么把请求体封装成了这种形式："userName=1&email=12"
            @RequestBody String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        return map;
    }

    // ----

    // 矩阵变量
    // springboot默认禁用掉了矩阵变量
    // 手动开启：
    // 原理：对于所有路径的处理，都是使用UrlPathHelper来进行解析的。
    // 它里面的removeSemicolonContent（移除分号内容）属性是用来支持矩阵变量的

    // 注意：矩阵变量是绑定在路径变量里的,因此一定要有url路径变量才能被解析

    // 语法1：/cars/sell;low=34;brand=byd,audi,yd
    @GetMapping("/cars/{path}")
    public Map<String, Object> carsSell(@MatrixVariable("low") Integer low,
                                        @MatrixVariable("brand") List<String> brand,
                                        @PathVariable("path") String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    // 语法2：/boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map<String, Object> bossAge(
            // 获取路径bossId下的矩阵变量
            @MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
            // 获取路径empId下的矩阵变量
            @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }

    // ---
    // POJO的封装
    // 数据绑定，无论是get/post提交的都可以和对象进行绑定
    // 注意：此时的前台的contentType是'application/x-www-form-urlencoded;charset=utf-8'
    @PostMapping("/saveUser")
    public Person saveUser(Person person) {
        return person;
    }
}
