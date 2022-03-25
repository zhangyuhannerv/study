package stack;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @ClassName LinkedListStackDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        // 测试ArrayStack是否正确
        // 先创建一个ArrayStack的对象->表示栈
        LinkedListStack listStack = new LinkedListStack();
        String key = "";
        boolean loop = true;// 控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show:表示显示栈");
            System.out.println("exit:表示退出程序");
            System.out.println("push:表示添加数据到栈（入栈）");
            System.out.println("pop:表示从栈中取出数据（出栈）");
            System.out.println("请输入您的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    listStack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    listStack.push(value);
                    break;
                case "pop":
                    try {
                        System.out.println("出栈的数据是" + listStack.pop());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");

    }
}

