package java_genericity.generic_typeErasure;

/**
 * @ClassName InfoImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class InfoImpl implements Info<Integer> {

    @Override
    public Integer info(Integer value) {
        return value;
    }
}
