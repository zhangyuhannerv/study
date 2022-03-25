package stack.calculator;

/**
 * @ClassName ArrayStack
 * @Description TODO
 * 定义一个ArrayStack，表示栈
 * 本栈是用来实现计算器代码的栈，需要扩展一些功能
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class ArrayStack2 {
    private int maxSize;// 栈的大小
    private int[] stack;// 数组模拟栈，数据就放在该数组中
    private int top = -1;// top为栈顶，初始化为-1，表示没有数据

    // 构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    // 增加一个方法，可以返回当前栈顶的值，但是，不是真正的pop出来
    public int peek() {
        return stack[top];
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

    // 返回运算符的优先级，优先级是程序员来确定，优先级使用数字越大，则优先级就越高
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        }
        if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;// 假定目前的表达式只有加减乘除
        }
    }

    // 判断是不是只有一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0;// 用于存放计算的结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;// 注意顺序，栈中后弹出的数字  运算符  栈中先弹出的数字
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}
















