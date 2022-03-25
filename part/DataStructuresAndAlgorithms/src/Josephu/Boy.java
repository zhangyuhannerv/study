package Josephu;

/**
 * @ClassName Boy
 * @Description 先创建一个Boy类，用来表示节点
 * @Author Zhangyuhan
 * @Date 2020/9/27
 * @Version 1.0
 */
public class Boy {
    private int no;// 编号
    private Boy next; // 指向下一个节点，默认是空

    public Boy(int no) {
        this.no = no;
    }

    public Boy() {
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
