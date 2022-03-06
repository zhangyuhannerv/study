package com.study.spring5_aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @ClassName JDKProxy
 * @Description 使用jdk代理实现接口功能增强
 * @Author Zhangyuhan
 * @Date 2022/3/6
 * @Version 1.0
 */
public class JDKProxy {
    public static void main(String[] args) {
        // 创建接口实现类的代理对象
        Class[] interfaces = {UserDao.class};
/*        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });*/


        UserDao userDao = new UserDaoImpl();
        UserDao userDao1 = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        userDao1.add(1, 2);
    }
}

// 创建代理对象代码
class UserDaoProxy implements InvocationHandler {
    private Object object;

    /**
     * 创建的是谁的代理对象，那么需要把那个谁给传递过来，通常是由有参构造传过来
     *
     * @param object
     */
    public UserDaoProxy(Object object) {
        this.object = object;
    }


    /**
     * 这里写增强的逻辑，只要该对象被创建，那么该方法就会被调用
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法之前做个处理
        System.out.println("方法之前执行" + method.getName() + ":传递的参数..." + Arrays.toString(args));

        // 被增强的方法指向
        Object res = method.invoke(object, args);

        // 方法之后做个处理
        System.out.println("方法之后执行：该方法返回结果是" + res);

        return res;
    }
}
