package bean;

import java.io.Serializable;

/**
 * @ClassName Master
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/9/24
 * @Version 1.0
 */
// 如果这里不实现Serializable接口，那么Dog实例在序列化时就会报错
public class Master implements Serializable {
}
