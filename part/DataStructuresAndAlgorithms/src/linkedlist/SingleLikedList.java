package linkedlist;

// 定义singleList来管理数据
public class SingleLikedList {
    // 先初始化一个头节点，头节点先不要动,不存放具体的数据
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }

    // 添加节点到单向的链表
    // 1.当不考虑编号的顺序时，找到当前链表的最后节点
    // 2.将最后节点的next域指向新的node即可
    public void add(HeroNode heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助遍历 temp
        HeroNode temp = head;
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
    }

    // 第二种添加节点的方式，根据编号选择位置按顺序添加
    // 如果已有排名，则添加失败，并给出提示
    public void addByOrder(HeroNode heroNode) {
        // 因为头节点不能动，因此我们任然通过一个辅助指针(变量）来帮助找到添加的位置
        // 因为单向链表，因此我们要找的temp要移动到添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        boolean flag = false; // 标识添加的编号是否存在，默认为false
        while (true) {
            // 必须先判断下一个节点是否为空，否则可能会报空指针异常
            if (temp.next == null) {// 说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) {// 位置找到了，就在temp的后面插入
                break;
            } else if (temp.next.no == heroNode.no) {// 说明希望添加的heroNode的编号已然存在
                flag = true;
                break;
            }
            temp = temp.next; // temp后移
        }

        // 判断flag的值
        if (flag) {// 不能添加，说明编号存在
            System.out.printf("要插入的英雄编号%d已经存在,不能加入\n", heroNode.no);
        } else {
            // 插入到 temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }


    }

    // 修改节点的信息，根据no编号来修改，no编号不能修改
    // 说明
    // 1. 根据newHeroNode的no来修改即可
    public void update(HeroNode newHeroNode) {
        // 判断是否为空
        /*if (head.next == null) {
            System.out.println("链表为空");
            return;
        }*/
        // 找到需要修改的节点，根据no编号
        // 先定义一个辅助变量
        // HeroNode temp = head.next;
        HeroNode temp = head;
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

    // 删除节点
    // 思路
    // 1.head节点不能动，因此需要一个temp辅助节点找到待删除节点的前一个节点
    // 2.我们在比较时，是temp.next.no和要删除的节点的no相比较
    public void del(int no) {
        HeroNode temp = head;
        boolean flag = false;// 标识是否找到待删除节点的前一个节点
        while (true) {
            if (temp.next == null) {// 已经到链表的最后
                break;
            }
            if (temp.next.no == no) {
                // 我们找到待删除节点的前一个节点
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) { // 说明找到
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的%d节点不存在\n", no);
        }
    }


    // 显示链表【遍历】
    public void list() {
        // 先判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点不能动，因此我们需要一个辅助变量来遍历
        // HeroNode temp = head.next;
        HeroNode temp = head;
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
}
