package double_linked_list;

import linkedlist.HeroNode;

public class DoubleLinkedListDemo {

    // 双向链表的测试
    public static void main(String[] args) {
        System.out.println("双向链表的测试");
        // 先创建节点
        HeroNode2 heroNode1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 heroNode2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 heroNode3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 heroNode4 = new HeroNode2(4, "林冲", "豹子头");

        // 创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode2);
        doubleLinkedList.add(heroNode3);
        doubleLinkedList.add(heroNode4);

        System.out.println("测试添加双向链表");
        doubleLinkedList.list();

        System.out.println("--------------------------");

        // 修改测试
        System.out.println("测试修改双向链表的某个节点");
        HeroNode2 heroNode5 = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(heroNode5);
        System.out.println("修改后的双线链表的情况");
        doubleLinkedList.list();

        System.out.println("---------------------");

        // 删除
        System.out.println("测试删除双向链表的某个节点");
        doubleLinkedList.del(3);
        doubleLinkedList.del(3);
        System.out.println("双向链表删除后的情况");
        doubleLinkedList.list();

        System.out.println("----------------------");
        // 测试有序插入新节点
        HeroNode2 heroNode6 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 heroNode7 = new HeroNode2(0, "张雨晗", "九龙神");
        HeroNode2 heroNode8 = new HeroNode2(5, "鲁智深", "花和尚");

        doubleLinkedList.addByOrder(heroNode6);
        doubleLinkedList.addByOrder(heroNode7);
        doubleLinkedList.addByOrder(heroNode8);
        System.out.println("有序插入后的链表顺序如下");
        doubleLinkedList.list();
    }

}
