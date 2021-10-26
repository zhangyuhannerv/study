package java_Lambda;

/**
 * @ClassName _0Employee
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/10/21
 * @Version 1.0
 */
public class _0Employee {
    private int id;
    private String name;
    private int age;
    private double salary;

    public _0Employee() {
    }

    public _0Employee(int id) {
        this.id = id;
    }

    public _0Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public _0Employee(int id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "_0Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
