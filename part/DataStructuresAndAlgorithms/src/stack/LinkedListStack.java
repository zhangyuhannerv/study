package stack;

import java.util.LinkedList;

/**
 * @ClassName LinkedListStack
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class LinkedListStack<T> {
    private LinkedList<T> linkedList = new LinkedList<>();
    private int top = -1;

    // 判断栈空
    public boolean isEmpty() {
        return linkedList.size() == 0;
    }

    // 入栈
    public void push(T t) {
        top++;
        linkedList.add(top, t);
    }

    // 出栈
    public T pop() {
        T t = linkedList.get(top);
        linkedList.remove(top);
        top--;
        return t;
    }

    // 遍历栈
    public void list() {
        for (int i = top; i >= 0; i--) {
            System.out.println(linkedList.get(i).toString());
        }
    }
}
