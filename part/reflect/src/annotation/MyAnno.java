package annotation;

import domain.Person;

/**
 * @ClassName MyAnno
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */
public @interface MyAnno {
    /*
    元注解（JDK已经定义了的注解）：
    用于描述注解的注解：
    @Target:描述注解能够作用的位置
    @Retention：描述注解被保留的阶段
    @Documented:描述注解是否被抽取到API文档中
    @Inherited：描述注解是否被子类继承
     */

    // 反编码格式如下
    /*
    public interface MyAnno extends java.lang.annotation.Annotation {
    }
    */

    // 因此我们说注解本质上就是一个接口
    // 接口中的抽象方法称为注解的属性
    // 要求：
    // 1.属性的返回值类型仅有几种取值
    //      基本数据类型
    //      字符串
    //      枚举
    //      注解
    //      以上几种类型的数组

    // 定义了属性，在使用时需要给属性（方法）赋值
    // int age();

    // 如果只有一个属性需要赋值，并且属性的名称是value，那么value可以省略，直接写值即可
    int value();

    String name() default "张三"; // 此时，使用注解时可以不用给name赋值，默认就是张三

    PersonENnum showPersonEnum();

    MyAnno2 showAnno();

    // 数组赋值时，值使用{}包裹。如果数组中只有一个值，则{}可以省略不写
    String[] showStrs();


}
