package stack;

/**
 * @ClassName Node
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class Node<T> {
    private T t;
    private Node next;

    public Node() {
    }

    public Node(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
