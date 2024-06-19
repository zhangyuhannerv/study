package java_genericity.generic_WildcardType.wildcardType_BaseUse;

/**
 * @ClassName Test
 * @Description 测试泛型通配符'?'的使用
 * 类型通配符一般是用?代替具体的类型实参。所以类型通配符是类型实参，而不是类型形参
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Box<Number> box1 = new Box<>();
        box1.setE(100);
        showBox(box1);

        Box<Integer> box2 = new Box<>();
        box2.setE(100);
        showBox(box2);// 此时报错
    }

    /**
     * Number是所有数字类的抽象父类
     *
     * @param box
     */
    // public static void showBox(Box<Number> box) {
    public static void showBox(Box<?> box) {// 解决传入Number可以，传入Integer不行的问题，引入泛型通配符'?'
        // Number e = box.getE();
        Object e = box.getE();
        System.out.println(e);
    }

    // 尝试方法重载，结果不行
    // 报错：提示和上面的方法具有相同的类型擦除
    // public static void showBox(Box<Integer> box) {
    //     Number e = box.getE();
    //     System.out.println(e);
    // }


}
