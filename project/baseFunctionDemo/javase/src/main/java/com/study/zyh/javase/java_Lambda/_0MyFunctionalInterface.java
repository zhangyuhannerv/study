package com.study.zyh.javase.java_Lambda;

/**
 * 自定义的函数式接口
 */

/*加不加注解只要只有一个抽像方法，都是函数式接口*/
/*加注解的目的就是为了校验一下这个到底是不是函数式接口*/
@FunctionalInterface
public interface _0MyFunctionalInterface {
    void method1();

    // 比如，写第二个方法的时候，注解会报错
    // void method2();
}
