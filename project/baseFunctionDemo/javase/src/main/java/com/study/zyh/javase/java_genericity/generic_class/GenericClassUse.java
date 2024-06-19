package java_genericity.generic_class;

import java.util.ArrayList;
import java.util.Random;

/**
 * @ClassName GenericClassUse
 * @Description 写一个简单的抽奖器来使用泛型类，从而加深对泛型类的理解
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */
public class GenericClassUse {
    public static void main(String[] args) {
        ProductGetter<String> stringProductGetter = new ProductGetter<>();
        // 预设奖品
        String[] addProducts = {"苹果手机", "华为手机", "扫地机器人", "咖啡机"};

        // 放入奖池
        for (String addProduct : addProducts) {
            stringProductGetter.addProduct(addProduct);
        }
        // 随机抽取5次
        for (int i = 0; i < 5; i++) {
            System.out.println(stringProductGetter.getProduct());
        }

        System.out.println("********************");

        ProductGetter<Integer> integerProductGetter = new ProductGetter<>();
        // 预设奖品
        Integer[] addPrice = {10000, 5000, 3000, 500, 100};
        // 放入奖池
        for (Integer integer : addPrice) {
            integerProductGetter.addProduct(integer);
        }
        // 抽奖5次
        for (int i = 0; i < 5; i++) {
            System.out.println(integerProductGetter.getProduct());
        }
    }
}

/**
 * 奖品泛型类:抽奖器
 *
 * @param <T>
 */
class ProductGetter<T> {
    // private T product;// 这就是一个奖品

    private ArrayList<T> list = new ArrayList<>();// 这是奖品池

    // 添加奖品
    public void addProduct(T t) {
        this.list.add(t);
    }

    // 抽奖
    public T getProduct() {
        return list.get(new Random().nextInt(this.list.size()));
    }
}
