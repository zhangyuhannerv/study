package thread;

/**
 * @ClassName Person
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/22
 * @Version 1.0
 */
public class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(name + i);
        }
    }
}
