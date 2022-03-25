package stack;

import java.util.LinkedList;

/**
 * @ClassName MyLinkedListDemo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class MyLinkedListDemo {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add("abc");
        linkedList.add(2);
        linkedList.add('b');
        System.out.println(linkedList.get(0));
        System.out.println(linkedList.get(1));
        System.out.println(linkedList.get(2));
        System.out.println("----------------------------");


        MyLinkedList myLinkedList = new MyLinkedList<>();
        String k = "1";
        String m = "aadab";
        char l = 'c';
        myLinkedList.add(k);
        myLinkedList.add(m);
        myLinkedList.add(l);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(new Person("张雨晗"));

        System.out.println(myLinkedList.getI(0));
        System.out.println(myLinkedList.getI(1));
        System.out.println(myLinkedList.getI(2));
        System.out.println(myLinkedList.getI(3));
        System.out.println(myLinkedList.getI(4));
        System.out.println(((Person) myLinkedList.getI(5)).getName());
    }
}
