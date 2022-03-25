package stack;

/**
 * @ClassName ArrayStack
 * @Description TODO
 * 定义一个ArrayStack，表示栈
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class ArrayStack {
    private int maxSize;// 栈的大小
    private int[] stack;// 数组模拟栈，数据就放在该数组中
    private int top = -1;// top为栈顶，初始化为-1，表示没有数据

    // 构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    // 判断栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 判断栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈push
    public void push(int num) {
        // 先判断栈是否满
        if (isFull()) {
            System.out.println("栈满");
            return;
        }

        top++;
        stack[top] = num;
    }

    // 出栈pop,将栈顶的数据返回
    public int pop() {
        if (isEmpty()) {
            // 抛出异常
            throw new RuntimeException("栈空，没有数据");
        }

        int value = stack[top];
        top--;
        return value;
    }

    // 显示栈的情况，即遍历栈
    // 遍历时，需要从栈顶开始显示数据（为了体现后入先出的思想）
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

}
















