package tree;

/**
 * @ClassName ArrBinaryTreeDemo
 * @Description 顺序存储二叉树
 * @Author Zhangyuhan
 * @Date 2020/11/29
 * @Version 1.0
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
        System.out.println();
        arrBinaryTree.infixOrder(0);
        System.out.println();
        arrBinaryTree.postOrder(0);

    }
}

// 编写一个ArrayBinaryTree,实现顺序存储二叉树的遍历
class ArrBinaryTree {
    private int[] arr;// 存储数据节点的数组

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    // 编写一个方法，完成顺序存储二叉树的一个前序遍历

    /**
     * @param index 表示数组的下标
     */
    public void preOrder(int index) {
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }

        // 输出当前这个元素
        System.out.print(arr[index] + ",");
        // 向左递归遍历
        if (index * 2 + 1 < arr.length) {
            preOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if (index * 2 + 2 < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder(int index) {
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }


        // 向左递归遍历
        if (index * 2 + 1 < arr.length) {
            infixOrder(index * 2 + 1);
        }
        // 输出当前这个元素
        System.out.print(arr[index] + ",");
        // 向右递归遍历
        if (index * 2 + 2 < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }

    public void postOrder(int index) {
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }


        // 向左递归遍历
        if (index * 2 + 1 < arr.length) {
            postOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if (index * 2 + 2 < arr.length) {
            postOrder(index * 2 + 2);
        }
        // 输出当前这个元素
        System.out.print(arr[index] + ",");
    }

    // 重载priOrder
    public void preOrder() {
        this.preOrder(0);
    }
}
