package tree;

import java.util.ArrayList;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/11/17
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 以ArrayList为例子，看看底层是怎么实现数组扩容的
        // ArrayList维护了数组 transient Object[] elementData;
        /**
         * ArrayList底层仍然是进行了数组扩容
         */
        ArrayList arrayList = new ArrayList();
    }
}
