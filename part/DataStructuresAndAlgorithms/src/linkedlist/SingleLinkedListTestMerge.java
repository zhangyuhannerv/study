package linkedlist;

import linkedlist.interview.question.Test;

/**
 * @ClassName SingleLinkedListTestMerge
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */
public class SingleLinkedListTestMerge {
    public static void main(String[] args) {
        SingleLikedList singleLikedList1 = new SingleLikedList();
        SingleLikedList singleLikedList2 = new SingleLikedList();

        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode heroNode5 = new HeroNode(5, "张雨晗", "九头虫");


        singleLikedList1.addByOrder(heroNode4);
        singleLikedList1.addByOrder(heroNode2);
        singleLikedList2.addByOrder(heroNode3);
        singleLikedList2.addByOrder(heroNode1);
        singleLikedList2.addByOrder(heroNode5);

        Test.mergeTwoLinkedList(singleLikedList1.getHead(), singleLikedList2.getHead()).list();
    }
}
