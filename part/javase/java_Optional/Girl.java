package java_Optional;

/**
 * @ClassName Girl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class Girl {
    private String name;


    public Girl() {
    }

    public Girl(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                '}';
    }
}
