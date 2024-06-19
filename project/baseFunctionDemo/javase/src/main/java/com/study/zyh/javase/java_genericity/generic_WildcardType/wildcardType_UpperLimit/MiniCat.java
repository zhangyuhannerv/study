package java_genericity.generic_WildcardType.wildcardType_UpperLimit;

/**
 * @ClassName MiniCat
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class MiniCat extends Cat {
    protected int level;

    public MiniCat() {
    }

    public MiniCat(String name, int age, int level) {
        super(name, age);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "MiniCat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", level=" + level +
                '}';
    }
}
