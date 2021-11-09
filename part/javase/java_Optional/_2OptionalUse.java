package java_Optional;

import org.junit.Test;

import java.util.Optional;

/**
 * @ClassName _2OptionalUse
 * @Description Optional的使用，观察它是如何避免空指针异常的
 * <p>
 * Optional类：为了在程序中避免出现空指针异常而创建的
 * <p>
 * 常用方法：
 * ofNullable(T t)
 * olElse(T other)
 * get():如果对象包含值，返回该值，否则抛出异常
 * isPresent():判断Optional实例是否包含对象。即对象为空返回false，否则返回true
 * <p>
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class _2OptionalUse {
    // 以前的一个方法
    public String getGirlName(Boy boy) {
        return boy.getGirl().getName();
    }

    // 以前的优化
    public String getGirlName1(Boy boy) {
        if (boy != null) {
            Girl girl = boy.getGirl();
            if (girl != null) {
                return girl.getName();
            }
        }
        return null;
    }

    // 使用Optional的优化
    // orElse(T other):如果Optional对象里面有值那么将其返回，否则返回指定的other对象(other是在boy是null情况下的备胎)
    public String getGirlName2(Boy boy) {
        Optional<Boy> boyOptional = Optional.ofNullable(boy);
        // 此时的boy1一定非空
        Boy boy1 = boyOptional.orElse(new Boy(new Girl("古力娜扎")));

        // 此时girl也可能是空的。因为存在boy不为空，但是boy的girl属性是空的情况
        Girl girl = boy1.getGirl();
        Optional<Girl> girlOptional = Optional.ofNullable(girl);

        // 此时的girl1一定非空
        Girl girl1 = girlOptional.orElse(new Girl("迪丽热巴"));

        return girl1.getName();

        // boy不空，girl不空的情况下，返回真实的girlName
        // boy不空，girl空，返回迪丽热巴
        // boy空，返回古力娜扎
    }

    @Test
    public void test() {
        Boy boy = new Boy();
        // 此时会报空指针异常
        // String girlName = getGirlName(boy);
        // System.out.println(girlName);

        // 此时会打印出null
        // String girlName = getGirlName1(boy);
        // System.out.println(girlName);

        // 打印古力娜扎
        Boy boy1 = null;
        System.out.println(getGirlName2(boy1));

        // 打印迪丽热巴
        Boy boy2 = new Boy(null);
        System.out.println(getGirlName2(boy2));

        // 打印马儿扎哈
        Boy boy3 = new Boy(new Girl("马儿扎哈"));
        System.out.println(getGirlName2(boy3));
    }

    // 测试 orElse(T other)方法
    @Test
    public void test1() {
        Girl girl1 = new Girl("真实姓名");
        girl1 = null;

        Optional<Girl> girlOptional = Optional.ofNullable(girl1);
        System.out.println(girlOptional);
        // orElse(T other):如果当前的Option内部封装的t是非空的，则返回内部的t。
        // 如果内部的t是空的，则返回orElse()方法中的参数other
        Girl girl = girlOptional.orElse(new Girl("哈哈"));
        System.out.println(girl);
    }
}
