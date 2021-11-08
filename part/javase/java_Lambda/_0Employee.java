package java_Lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static List<_0Employee> getData() {
        List<_0Employee> list = new ArrayList<>();
        list.add(new _0Employee(1, "a", 20, 3000));
        list.add(new _0Employee(2, "b", 19, 8000));
        list.add(new _0Employee(3, "c", 21, 7500));
        list.add(new _0Employee(4, "d", 18, 6000));
        list.add(new _0Employee(5, "e", 24, 7000));
        list.add(new _0Employee(5, "e1", 24, 6000));
        list.add(new _0Employee(5, "比尔盖茨", 24, 7000));
        list.add(new _0Employee(5, "扎克伯格", 24, 7000));
        return list;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _0Employee employee = (_0Employee) o;
        return id == employee.id && age == employee.age && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name == null ? name.hashCode() : 0);
        result = 31 * result + age;
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
