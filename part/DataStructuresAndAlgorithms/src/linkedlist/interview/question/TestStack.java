package linkedlist.interview.question;

import java.util.Stack;

/**
 * @ClassName TestStack
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */
public class TestStack {
    // 粗略演示一下栈
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        // 入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        // 出栈
        // smith，tom，jack
        while (stack.size() > 0) {
            System.out.println(stack.pop());// pop就是将栈顶的数据取出
        }
    }
}
