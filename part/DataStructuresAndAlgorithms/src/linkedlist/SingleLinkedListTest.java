package linkedlist;

import linkedlist.interview.question.Test;

/**
 * @ClassName SingleLinkedList
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/17
 * @Version 1.0
 */
public class SingleLinkedListTest {
    public static void main(String[] args) {
        // 进行测试
        // 先创建节点
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");


        // 创建一个链表
        SingleLikedList singleLikedList = new SingleLikedList();
        // 加入
        /*singleLikedList.add(heroNode1);
        singleLikedList.add(heroNode4);
        singleLikedList.add(heroNode2);
        singleLikedList.add(heroNode3);*/

        System.out.println("_____________________________________");

        // 无序插入，有序输出
        singleLikedList.addByOrder(heroNode1);
        singleLikedList.addByOrder(heroNode4);
        singleLikedList.addByOrder(heroNode1);
        singleLikedList.addByOrder(heroNode2);
        singleLikedList.addByOrder(heroNode3);

        // 测试正常输出链表
        singleLikedList.list();
        System.out.println("----------------------------------------");

        // 测试反转链表
        Test.reverseList(singleLikedList.getHead());

        // 显示链表里面的元素
        singleLikedList.list();
        System.out.println("--------------------逆序打印--------------------");
        // 测试逆序打印单链表，没有改变链表本身的结构

        Test.reversePrint(singleLikedList.getHead());


        System.out.println("-------------------------------------");

        // 测试修改代码的节点
        singleLikedList.update(new HeroNode(3, "小卢", "魔域麒麟 "));

        // 显示修改后的链表里面的元素
        singleLikedList.list();

        System.out.println("----------------------------------------");

        // 测试删除链表的节点
        singleLikedList.del(1);
        // singleLikedList.del(2);
        // singleLikedList.del(3);
        singleLikedList.del(4);
        singleLikedList.del(5);

        // 显示删除链表后里面的元素
        singleLikedList.list();

        System.out.println("_________________________________________________");
        // 测试获取链表发长度的方法
        System.out.println(Test.getLength(singleLikedList.getHead()));
        System.out.println("-------------------------------------------------");
        // 获取倒数第k个节点
        System.out.println(Test.findLastIndexNode(singleLikedList.getHead(), 1));
        System.out.println(Test.findLastIndexNode(singleLikedList.getHead(), 2));
        System.out.println(Test.findLastIndexNode(singleLikedList.getHead(), 3));
        System.out.println(Test.findLastIndexNode(singleLikedList.getHead(), 0));
    }


}

