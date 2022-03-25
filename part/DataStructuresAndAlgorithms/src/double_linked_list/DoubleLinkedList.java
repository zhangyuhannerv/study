package double_linked_list;

import linkedlist.HeroNode;

/**
 * @ClassName DoubleLinkedList
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/27
 * @Version 1.0
 */

// 创建一个双向链表的类
public class DoubleLinkedList {

    private HeroNode2 head = new HeroNode2(0, "", "");

    public HeroNode2 getHeroNode2() {
        return head;
    }

    public void setHeroNode2(HeroNode2 heroNode2) {
        this.head = heroNode2;
    }

    // 遍历双向链表的方法
    // 显示链表【遍历】
    public void list() {
        // 先判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点不能动，因此我们需要一个辅助变量来遍历
        // HeroNode temp = head.next;
        HeroNode2 temp = head;
        while (true) {
            // 判断是否为链表的最后
            if (temp.next == null) {
                break;
            }

            // 输出节点信息
            System.out.println(temp.next);

            // 将temp后移，
            temp = temp.next;

        }

    }

    // 添加节点到双向链表的最后
    public void add(HeroNode2 heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助遍历 temp
        HeroNode2 temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 如果没有找到最后,就将temp后移
            temp = temp.next;
        }
        // 当退出while循环时，temp就指向链表的最后
        // 将最后节点的next指向新的节点
        temp.next = heroNode;
        // 形成一个双向链表所必要做的
        heroNode.pre = temp;
    }

    // 将双向链表按照编号的顺序进行添加
    public void addByOrder(HeroNode2 heroNode2) {
        if (head.next == null) {
            head.next = heroNode2;
            heroNode2.pre = head;
            return;
        }
        HeroNode2 temp = head.next;
        while (true) {
            if (temp.no > heroNode2.no) {
                // 新节点与前节点建立练习
                temp.pre.next = heroNode2;
                heroNode2.pre = temp.pre;
                // 新节点与后节点建立练习
                temp.pre = heroNode2;
                heroNode2.next = temp;
                break;
            }

            if (temp.next == null) {// 最后一个节点
                temp.next = heroNode2;
                heroNode2.pre = temp;
                break;
            }

            temp = temp.next;
        }
    }


    // 修改双向链表，和单向链表基本上是一样的，只是节点的类型不同
    // 修改节点的信息，根据no编号来修改，no编号不能修改
    // 说明
    // 1. 根据newHeroNode的no来修改即可
    public void update(HeroNode2 newHeroNode) {
        // 找到需要修改的节点，根据no编号
        // 先定义一个辅助变量
        HeroNode2 temp = head;
        boolean flag = false; // 标识是否找到改节点
        while (true) {
            if (temp.next == null) {
                break;// 链表遍历结束
            }
            if (temp.next.no == newHeroNode.no) {
                // 找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        // 根据flag判断，是否temp即为要修改的节点
        if (flag) {
            temp.next.name = newHeroNode.name;
            temp.next.nickName = newHeroNode.nickName;
        } else {// 没有找到
            System.out.printf("没有找到编号%d的节点，不能修改\n", newHeroNode.no);
        }
    }

    // 删除双向链表的节点
    // 说明：
    // 1.对于双向链表，我们可以直接找到要删除的这个节点
    // 2.找到后，自我删除即可

    public void del(int no) {
        // 判断当前链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法删除");
        }

        HeroNode2 temp = head.next;
        boolean flag = false;// 标识是否找到待删除节点的前一个节点
        while (true) {
            if (temp == null) {// 已经到链表的最后
                break;
            }
            if (temp.no == no) {
                // 我们找到待删除的节点
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) { // 说明找到
            temp.pre.next = temp.next;
            // 这里代码有问题
            // 如果删除的是最后的一个节点，就不需要执行下面这个代码了，否则会报空指针异常
            // temp.next.pre = temp.pre;
            // 所以要改成如下形式
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }

        } else {
            System.out.printf("要删除的%d节点不存在\n", no);
        }
    }

}


