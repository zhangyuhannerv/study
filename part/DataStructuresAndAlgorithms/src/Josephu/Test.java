package Josephu;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 测试一把看看构建环形链表和遍历环形链表是否ok
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        // circleSingleLinkedList.addBoy(5);// 加入5个小孩节点
        circleSingleLinkedList.addBoy(125);// 加入125个小孩节点
        circleSingleLinkedList.showBoy();


        System.out.println("--------------------------------------");

        // 测试一把小孩出圈是否正确
        // circleSingleLinkedList.countBoy(1, 2, 5);// 2，4，1，5，3
        circleSingleLinkedList.countBoy(10, 20, 125);

    }
}
