package thread.thread.notifyAndWaitExample;

/**
 * @ClassName Baozi
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class Baozi {
    // 皮
    private String pi;
    // 馅
    private String xian;
    // 包子的状态： 有：true，没有：false
    // 设置初始类型为false
    boolean flag = false;

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
