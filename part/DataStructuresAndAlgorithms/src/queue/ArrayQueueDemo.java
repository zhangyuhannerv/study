package queue;

import java.util.Scanner;

/**
 * @ClassName ArrayQueueDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/8
 * @Version 1.0
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        //测试数组队列
        //创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' ';//接受用户输入
        Scanner input = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop) {
            System.out.println("s(show:):显示队列");
            System.out.println("e(exit:):退出程序");
            System.out.println("a(add:):添加数据到队列");
            System.out.println("g(get:):从队列中取出数据");
            System.out.println("h(head):查看队列头的数据");
            key = input.next().charAt(0);//接受一个字符
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数据");
                    int value = input.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g'://取出数据
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h'://查看队列头的数据是什么
                    try {
                        int res = arrayQueue.hideQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    input.close();
                    loop = false;
                    break;
                default:
                    break;

            }

        }
        System.out.println("程序退出");
    }


}
