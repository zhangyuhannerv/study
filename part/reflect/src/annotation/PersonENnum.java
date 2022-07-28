package annotation;

/**
 * @ClassName PersonENnum
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */
public enum PersonENnum {
    P1("张三", "男"),
    P2("李四", "女");

    private String name;
    private String sex;

    PersonENnum(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    PersonENnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
