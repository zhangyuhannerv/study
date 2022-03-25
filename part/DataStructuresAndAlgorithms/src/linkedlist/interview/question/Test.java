package linkedlist.interview.question;

import linkedlist.HeroNode;
import linkedlist.SingleLikedList;

import java.util.Stack;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */
public class Test {
    // 方法：获取到单链表的有效节点的个数，（去掉头节点）
    public static int getLength(HeroNode head) {
        if (head.next == null) {// 空链表
            return 0;
        }
        int length = 0;
        // 定义一个辅助的变量
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }


    // 方法：查找单链表倒数第k个节点
    // 思路：
    // 1. 编写一个方法，接收head节点，同时接收一个index
    // 2.index表示倒数的第index个节点
    // 3.获得链表的总长度
    // 4.得到size 后，遍历（size-index）个后就可以得到
    // 如果找到返回该节点，找不到返回null
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        // 如果链表为空，返回null
        if (head.next == null) {
            return null; //表示没有找到
        }
        // 定义辅助变量
        HeroNode temp = head.next;
        // 得到链表的长度
        int length = getLength(head);
        // 做一个index的校验
        if (index <= 0 || index > length) {
            return null;
        }

        // for循环定位到要找的节点上
        for (int i = 0; i < length - index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    // 方法：单链表的反转，有一点难度
    public static void reverseList(HeroNode head) {
        // 如果当前链表为空，或者只有一个节点，jiu无需反转直接返回
        if (getLength(head) == 0 || getLength(head) == 1) {
            return;
        }
        // 定义一个辅助的指针（变量）帮我们遍历原来的链表
        HeroNode temp = head.next;
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, "", "");

        // 遍历原来的链表，没遍历一个节点，就将其取出，放在新链表reverseHead的最前端
        while (temp != null) {
            next = temp.next;// 先暂时保存当前节点的下一个节点，因为后面需要使用
            temp.next = reverseHead.next;// 将temp的下一个节点指向新的链表的头部的下一个（最前端）
            reverseHead.next = temp;// 将temp接到新的链表上
            temp = next;
        }
        // 将head.next指向reverseHead.next,// 实现了单链表的反转
        head.next = reverseHead.next;
        reverseHead = null;
    }

    // 使用方式2逆序打印一个单向链表
    // 可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，实现逆序打印的效果
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return; //空链表不能打印
        }
        // 创建一个栈，将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = head.next;
        // 将链表的所有节点压入栈
        while (temp != null) {
            stack.push(temp);
            temp = temp.next; // 临时节点后移
        }
        // 将栈中的节点进行打印，pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());// 出来一个节点，就打印一个节点，栈的特点是先进后出
        }
    }


    // 方法：合并两个有序的单链表
    public static SingleLikedList mergeTwoLinkedList(HeroNode node1, HeroNode node) {

        SingleLikedList singleLikedList = new SingleLikedList();
        if (node.next == null && node1.next == null) {
            return singleLikedList;
        } else if (node.next == null) {
            singleLikedList.setHead(node1);
            return singleLikedList;
        } else if (node1.next == null) {
            singleLikedList.setHead(node);
            return singleLikedList;
        }

        HeroNode temp = node.next;
        HeroNode temp1 = node1.next;
        HeroNode next = null;
        HeroNode next1 = null;


        HeroNode newHead = new HeroNode(0, "", "");
        singleLikedList.setHead(newHead);


        while (true) {


            if (temp == null && temp1 == null) {
                break;
            } else if (temp == null) {
                // 获取当前节点的下一个节点
                next1 = temp1.next;
                // 让头指针指向当前节点的下一个节点
                node1.next = next;
                // 让取出来的节点是一个独立的节点
                temp1.setNext(null);
                // 往新链表里添加取出的节点
                singleLikedList.add(temp1);
                // 获取下一个节点
                temp1 = next1;
            } else if (temp1 == null) {
                next = temp.next;
                temp.setNext(null);
                node.next = next;
                singleLikedList.add(temp);
                temp = next;
            } else if (temp.no < temp1.no) {
                next = temp.next;
                temp.setNext(null);
                node.next = next;
                singleLikedList.add(temp);
                temp = next;
            } else if (temp.no >= temp1.no) {
                next1 = temp1.next;
                temp1.setNext(null);
                node1.next = next;
                singleLikedList.add(temp1);
                temp1 = next1;
            }
        }


        return singleLikedList;

    }

}
