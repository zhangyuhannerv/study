package hashTable;

import java.util.Scanner;

/**
 * @ClassName HashTableDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/11/16
 * @Version 1.0
 */
public class HashTableDemo {
    public static void main(String[] args) {
        // 测试
        // 创建哈希表
        HashTable hashTable = new HashTable(7);
        // 写一个简单的菜单来测试
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("exit:退出系统");
            System.out.println("find:查找雇员");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    // 创建雇员
                    hashTable.add(new Emp(id, name));
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                case "find":
                    System.out.println("请输入要查找的id");
                    hashTable.findEmpById(scanner.nextInt());
                    break;
                default:
                    break;
            }
        }
    }
}

// 创建HashTable,管理多条链表
class HashTable {
    private EmpLinkedList[] empLinkedListArray;
    private int size; // 表示共有多少条链表

    // 构造器
    public HashTable(int size) {
        this.size = size;
        // 初始化链表数组
        empLinkedListArray = new EmpLinkedList[size];
        // 留一个坑,如果没有下面的代码，直接往哈希表中添加对象会报空指针异常，所以，↓
        // 注意：不要忘记分别初始化每一条链表
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    // 添加雇员
    public void add(Emp emp) {
        // 根据员工的id,得到该员工应该添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        // 将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 编写一个散列函数，使用一个简单的取模法
    public int hashFun(int id) {
        return id % size;
    }

    // 遍历所有的链表（遍历hashTable)
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    // 根据输入的id查找雇员
    public void findEmpById(int id) {
        // 使用散列函数确定到哪条链表查找
        int empLinkedListNo = hashFun(id);
        Emp emp = empLinkedListArray[empLinkedListNo].findEmpById(id);
        if (emp != null) { // 找到了
            System.out.println("在第" + (empLinkedListNo + 1) + "条链表中找到该雇员，id=" + id);
        } else {
            System.out.println("在哈希表中没有找到该雇员");
        }
    }
}

// 表示一个雇员类
class Emp {
    public int id;
    public String name;
    public Emp next; // 构造器里没有next,是默认为空的（null）

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Emp() {
    }
}

// 创建一个EmpLinkedList,表示链表
class EmpLinkedList {
    // 头指针，指向第一个雇员，因此我们这个链表的head是直接指向第一个emp的
    private Emp head;// 默认为空

    // 添加雇员到链表
    // 说明：

    /**
     * 1.假定：当添加雇员时，id是自增长的，即id的分配总是从小到大
     * 2.因此我们将该雇员直接加入到本链表的最后即可
     */
    public void add(Emp emp) {
        // 如果是添加第一个雇员
        if (head == null) {
            head = emp;
            return;
        }

        // 如果不是添加第一个雇员则，则使用一个辅助的指针，帮助定位到最后
        Emp currentEmp = head;
        while (true) {
            if (currentEmp.next == null) {// 说明到链表的最后了
                break;
            }
            currentEmp = currentEmp.next; // 后移
        }
        // 退出时直接将emp加到链表的最后即可
        currentEmp.next = emp;
    }

    // 遍历链表的雇员信息
    public void list(int no) {
        if (head == null) { // 说明链表为空
            System.out.println("第" + (no + 1) + "条链表为空");
            return;
        }
        System.out.print("第" + (no + 1) + "链表的信息为:");
        Emp curEmp = head;// 辅助指针
        while (true) {
            System.out.printf("=> id=%d name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {// 说明curEmp是最后节点
                break;
            }
            curEmp = curEmp.next;// 后移，遍历
        }
        System.out.println();
    }

    // 根据id查找雇员
    // 如果查找到就返回emp
    // 如果没有找到就返回null
    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("链表空");
            return null;
        }

        // 辅助指针
        // 假定id不重复
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {// 找到
                break;// 这时curEmp就指向要查找的雇员
            }
            if (curEmp.next == null) {// 说明当前链表没有找到该雇员
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;// 后移
        }
        return curEmp;
    }
}
