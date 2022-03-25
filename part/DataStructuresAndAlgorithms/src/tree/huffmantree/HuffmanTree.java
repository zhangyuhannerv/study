package tree.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName HuffmanTree
 * @Description 霍夫曼树(只有二叉树)
 * @Author Zhangyuhan
 * @Date 2021/1/31
 * @Version 1.0
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        // 测试一把
        root.preOrder(); // 67，29，38，15，7，8，23，10，4，1，3，6，13
        // 测试正确bing go~~
    }


    // 由数组创建霍夫曼树的方法

    /**
     * createHuffmanTree
     *
     * @param arr 需要创建霍夫曼树的数组
     * @return 返回的是创建好后的霍夫曼树的root节点
     * @author Zhangyuhan
     * @date 2021/2/1 0:07
     */
    public static Node createHuffmanTree(int[] arr) {
        // 第一步，为了操作方便
        // 1.遍历arr数组
        // 2.将arr的每个元素构建成一个Node
        // 3.将node放入到ArrayList中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }


        // 我们构建霍夫曼树的过程是一个循环的过程
        // 第二步，构建霍夫曼树
        while (nodes.size() > 1) {
            // 0.每次都要先排序
            // 排序 从小到大
            // 这里之所以能排序node对象，是因为我们重写了比较器
            Collections.sort(nodes);
            // 1.取出权值最小的节点（一个节点也可以看成是最简单的二叉树）
            Node leftNode = nodes.get(0);
            // 2.取出第二小的节点
            Node rightNode = nodes.get(1);
            // 3.构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            // 4.从nodes删除掉处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 5.将parent加入到nodes中去
            nodes.add(parent);
        }

        // 最后返回霍夫曼树的root节点
        return nodes.get(0);
    }

    // 编写一个前序遍历的方法
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("树是空树，不能遍历~");
        }
    }
}

// 创建节点类
// 为了让node支持排序，Collections集合排序，让node实现Comparable接口
class Node implements Comparable<Node> {
    int value;// 节点的权值
    // 本类中没有用到这个字符，这是讲后来霍夫曼编码时顺便加上的，可以忽略掉它
    char c; // 这是字符
    Node left;// 指向左子节点
    Node right;// 指向右子节点

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        // 表示升序排序
        return this.value - o.value;
        // 降序排序
        // return o.value - this.value;
    }

    // 写一个节点前序遍历的方法
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}
