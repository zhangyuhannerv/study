package java_genericity.generic_WildcardType.wildcardType_UpperLimit;

/**
 * @ClassName Test
 * @Description 测试类型通配符的上限
 * 类型通配符的上限：
 * 泛型标识为<? extends 实参类型>
 * 要求该泛型类型只能为实参类型，或者实参类型的子类类型
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class BaseUse {
    public static void main(String[] args) {
        TestClass<Integer> integerTestClass = new TestClass<>();
        integerTestClass.setE(200);
        TestClass.show(integerTestClass);
    }
}

class TestClass<E> {
    private E e;

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    /**
     * ？只能是Number或者Number的子类
     *
     * @param testClass
     */
    public static <E extends Number> void show(TestClass<E> testClass) {
        Number e = testClass.getE();
        System.out.println(e);
    }
}
