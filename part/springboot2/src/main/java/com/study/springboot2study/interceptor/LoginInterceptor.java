package com.study.springboot2study.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginInterceptor
 * @Description 定义登陆拦截器（登陆检查）
 * 1.配置好拦截器要拦截哪些请求
 * 2.把这些配置放在容器中
 * @Author Zhangyuhan
 * @Date 2022/4/21
 * @Version 1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截的请求是：" + request.getRequestURI());

        // 登陆检查逻辑
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            // 放行
            return true;
        }


        // 拦截住，那么此时是未登录，跳转到登录页

        // 这种写法是重定向，此时往session里放值，前台可能会取不到message
        // session.setAttribute("msg", "请先登录");// 给登录页一个消息提示
        // response.sendRedirect(request.getContextPath() + "/");// 重定向到登录页

        // 优化后改为转发(注意：转发不需要获取contextPath，这点和重定向不同）
        request.setAttribute("msg", "请先登陆");
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }

    /**
     * 目标方法执行之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info("postHandle{}", modelAndView);
    }

    /**
     * 页面渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        log.info("afterCompletion{}", ex);
    }
}
