package com.study.zyh.javase.java_genericity.generic_interface;

/**
 * 泛型接口
 * 1.定义语法
 * interface 接口名称<泛型标识，泛型标识，.....>{
 * 泛型标识 方法名();
 * .....
 * }
 * 2.使用泛型接口
 * 2.1实现类不是泛型类，接口要明确数据泛型标识
 * 2.2实现类也是泛型类，实现类和接口的泛型标识要一致(类比泛型的派生子类)
 */


/**
 * 泛型接口
 *
 * @param <T>
 */
public interface GenericInterface<T> {
    T getKey();
}

// 实现类不是泛型类。此时接口的泛型标识是Object。
// class Apple implements GenericInterface {
//     @Override
//     public Object getKey() {
//         return null;
//     }

// 实现类不是泛型类。此时接口的泛型标识是String。
class Apple implements GenericInterface<String> {
    @Override
    public String getKey() {
        return "hello";
    }
}

/**
 * 泛型接口的实现类是一个泛型类,此时接口的泛型标识必须是实现类的泛型标识中的一个
 *
 * @param <T>
 * @param <E>
 * @param <K>
 */
class Pear<T, E, K> implements GenericInterface<E> {
    private T t;
    private E e;
    private K k;

    public Pear() {
    }

    public Pear(T t, E e, K k) {
        this.t = t;
        this.e = e;
        this.k = k;
    }

    @Override
    public E getKey() {
        return e;
    }

    public void setE(E key) {
        this.e = key;
    }

    public E getE() {
        return e;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }
}

