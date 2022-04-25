package com.study.springboot2study.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyServlet
 * @Description spring注入web原生三组件之Servlet
 * @Author Zhangyuhan
 * @Date 2022/4/26
 * @Version 1.0
 */

@WebServlet(urlPatterns = "/myServlet")
// 效果：直接响应，没有经过springboot的拦截器
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("666");
    }
}
