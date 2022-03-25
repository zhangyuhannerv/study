package stack;

/**
 * @ClassName MyLinkedList
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class MyLinkedList<T> {
    private Node<T> first;
    private int length;

    public void add(T t) {
        if (first == null) {
            first = new Node<>(t);
            length++;
            return;
        }

        Node curr = first;

        while (true) {
            if (curr.getNext() == null) {
                break;
            }
            curr = curr.getNext();
        }

        curr.setNext(new Node(t));
        length++;

    }

    public T getI(int i) {
        if (i < 0 || i > length - 1) {
            throw new RuntimeException("i值越界");
        }
        int j = 0;
        Node temp = first;
        while (j != i) {
            temp = temp.getNext();
            j++;
        }
        return (T) temp.getT();
    }
}
