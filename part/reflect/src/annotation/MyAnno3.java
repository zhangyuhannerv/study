package annotation;

import java.lang.annotation.*;

/**
 * 元注解（JDK已经定义了的注解）：
 * 用于描述注解的注解：
 *
 * @Target:描述注解能够作用的位置 ELementType取值：
 * TYPE：可以作用于类上
 * METHOD：可以作用于方法上
 * FIELD：可以作用于成员变量上
 * @Retention：描述注解被保留的阶段
 * @Documented:描述注解是否被抽取到API文档中
 * @Inherited：描述注解是否被子类继承
 */

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD}) // 表示MyAnno3注解只能作用于类和成员变量和方法上
@Retention(RetentionPolicy.RUNTIME)// 当前被它所描述的注解，会保留到class字节码文件中，并被JVM读取到，即在程序运行时，JVM能读取到这个注解
@Documented
@Inherited
public @interface MyAnno3 {


}
