package double_linked_list;

//定义HeroNode，每个HeroNode对象就是一个节点
public class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next;// 指向下一个节点
    public HeroNode2 pre;// 指向前一个节点

    public HeroNode2 getNext() {
        return next;
    }

    public void setNext(HeroNode2 next) {
        this.next = next;
    }

    public HeroNode2 getPre() {
        return pre;
    }

    public void setPre(HeroNode2 pre) {
        this.pre = pre;
    }

    // 构造器
    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}