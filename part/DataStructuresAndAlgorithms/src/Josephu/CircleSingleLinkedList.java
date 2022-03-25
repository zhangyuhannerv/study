package Josephu;

/**
 * @ClassName CircleSingleLinkedList
 * @Description TODO
 * 单向的环形链表
 * @Author Zhangyuhan
 * @Date 2020/9/27
 * @Version 1.0
 */
public class CircleSingleLinkedList {
    // 创建first节点，当前没有编号
    private Boy first;

    // 添加小孩的节点，构建成一个环形的链表
    public void addBoy(int nums) {
        // 对nums做一个数据校验
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }

        Boy curBoy = null;// 辅助指针，帮助构建环形链表

        // 使用for循环来创建我们的环型链表
        for (int i = 1; i <= nums; i++) {
            // 根据编号创建小孩节点
            Boy boy = new Boy(i);
            // 如果是第一个小孩
            if (i == 1) {
                first = boy;
                first.setNext(first);// 构成一个节点的环形链表
                curBoy = first;// 让curBoy指向第一个小孩
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    // 遍历出当前所有的节点(遍历环形链表）
    public void showBoy() {
        // 判断链表是否为空
        if (first == null) {
            System.out.println("链表为空，没有任何小孩");
            return;
        }

        // 因为first不能动，因此我们任然使用一个辅助指针完成遍历
        Boy curBoy = first;
        while (true) {
            System.out.printf("小孩的编号是%d\n", curBoy.getNo());
            if (curBoy.getNext() == first) {// 说明已经遍历完毕
                break;
            }
            curBoy = curBoy.getNext();// curBoy后移
        }
    }

    // 根据用户的输入，计算出小孩出圈的一个顺序

    /**
     * @param startNo  表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums     表示最开始有多少小孩在圈中（用来校验）
     */
    public void countBoy(int startNo, int countNum, int nums) {
        // 先对数据进行校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("链表为空或您的参数输入有误，请重新输入");
            return;
        }
        // 创建一个辅助指针，帮助小孩完成出圈
        Boy helper = first;
        // 让helper指向first的前一个节点
        while (true) {
            if (helper.getNext() == first) {// 说明helper已经指向了最后的小孩节点
                break;
            }
            helper = helper.getNext();
        }
        // 小孩报数前，先让first和helper移动startNo-1次
        // first和helper的重定位
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        // 当小孩报数时，让first和helper指针同时移动countNum-1次，然后出圈
        // 这里是一个循环的操作，直到圈中只有一个节点
        while (true) {
            if (helper == first) {// 说明圈中只有一个节点
                break;
            }
            // 让first和helper同时移动countNum-1次，然后出圈
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 这时first指向的节点，就是要出圈的小孩节点
            System.out.printf("小孩%d出圈\n", first.getNo());
            // 这时将first指向小孩节点出圈
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.printf("最后留在圈中的小孩编号%d\n", first.getNo());// 此时first和helper指向同一个节点


    }


}
