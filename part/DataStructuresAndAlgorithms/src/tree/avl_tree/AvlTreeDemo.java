package tree.avl_tree;

/**
 * @ClassName AvlTreeDemo
 * @Description 平衡二叉树
 * @Author Zhangyuhan
 * @Date 2021/2/20
 * @Version 1.0
 */
public class AvlTreeDemo {
    public static void main(String[] args) {
        // 左旋测试
       /* int[] arr = {4, 3, 6, 5, 7, 8};
        // 创建一个avlTree对象
        AvlTree avlTree = new AvlTree();
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        // 中序遍历
        System.out.println("----中序遍历----");
        avlTree.infixOrder();

        System.out.println("在做平衡处理之后~~");
        System.out.println("树的高度" + avlTree.getRoot().height());
        System.out.println("左子树的高度" + avlTree.getRoot().left.height());
        System.out.println("右子树的高度" + avlTree.getRoot().right.height());*/

        // 右旋测试
       /* int[] rightArr = {10, 12, 8, 9, 7, 6};
        AvlTree avlTree = new AvlTree();
        for (int i = 0; i < rightArr.length; i++) {
            avlTree.add(new Node(rightArr[i]));
        }
        // 中序遍历
        System.out.println("----中序遍历----");
        avlTree.infixOrder();

        System.out.println("在做平衡处理之后~~");
        System.out.println("树的高度" + avlTree.getRoot().height());
        System.out.println("左子树的高度" + avlTree.getRoot().left.height());
        System.out.println("右子树的高度" + avlTree.getRoot().right.height());
        System.out.println("当前的根节点是" + avlTree.getRoot());*/

        // 双旋测试
        int[] rightArr = {10, 11, 7, 6, 8, 9};
        AvlTree avlTree = new AvlTree();
        for (int i = 0; i < rightArr.length; i++) {
            avlTree.add(new Node(rightArr[i]));
        }
        // 中序遍历
        System.out.println("----中序遍历----");
        avlTree.infixOrder();

        System.out.println("在做平衡处理之后~~");
        System.out.println("树的高度" + avlTree.getRoot().height());
        System.out.println("左子树的高度" + avlTree.getRoot().left.height());
        System.out.println("右子树的高度" + avlTree.getRoot().right.height());
        System.out.println("当前的根节点是" + avlTree.getRoot());
        System.out.println("根节点的左子节点" + avlTree.getRoot().left);
        System.out.println("根节点的左子节点的左子节点" + avlTree.getRoot().left.left);
    }
}

// 创建avlTree
class AvlTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    // 查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    // 查找要删除的节点的父节点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    // 编写方法
    // 1.返回的是以node为根节点的二叉排序树的最小节点的值
    // 2.删除以node为根节点的二叉排序树的最小节点

    /**
     * @param node 传入的节点（当做二叉排序树的根节点）
     * @return 返回的是以node为根节点的二叉排序树的最小节点的值
     */
    public int deleteRightTreeMin(Node node) {
        Node target = node;
        // 循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        // 这时target就指向了最小的节点
        // 删除最小节点
        delNode(target.value);
        return target.value;
    }

    // 删除节点
    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            // 1.需要先找到要删除的节点
            Node targetNode = search(value);
            // 如果没有找到要删除的节点
            if (targetNode == null) {
                return;
            }
            // 当该树只有一个root节点并且target节点不为空的时候，说明root就是target节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            // 2.查找targetNode的父节点
            Node parent = searchParent(value);


            if (targetNode.left == null && targetNode.right == null) {  // 3.如果要删除的节点是叶子节点
                // 判断targetNode是父节点的左子节点还是右子节点
                if (parent.left != null && parent.left == targetNode) {// 是左子节点
                    parent.left = null;
                } else if (parent.right != null && parent.right == targetNode) {// 是右子节点
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {// 删除有两颗子树的节点
                // 找到targetNode节点右子树的最小值，并删除最小值所在的节点
                int minValue = deleteRightTreeMin(targetNode.right);
                // 重置targetNode的value
                // 注意：如果不愿意右边找最小的，也可以左边找最大的
                targetNode.value = minValue;
            } else {// 删除只有一颗子树的节点
                // 如果要删除的节点有左子节点
                if (targetNode.left != null) {
                    if (parent == null) {// targetNode就是root
                        root = root.left;
                    } else if (parent.left.value == value) { // 如果targetNode是parent的左子节点
                        parent.left = targetNode.left;
                    } else {// targetNode是parent的右子节点
                        parent.right = targetNode.left;
                    }
                } else {// 要删除的节点只有右子节点
                    if (parent == null) {
                        root = root.right;
                    } else if (parent.left.value == value) {
                        parent.left = targetNode.right;
                    } else {
                        parent.right = targetNode.right;
                    }
                }
            }
        }
    }

    public void add(Node node) {
        // 如果root为空，直接让root指向node
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("当前bst树为空，不能遍历");
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    // 返回当前节点左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    // 返回当前节点右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    // 返回以当前节点为根节点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    // 左旋方法
    private void leftRotate() {
        // 1.创建新的节点，以当前跟节点的值创建
        Node newNode = new Node(this.value);
        // 2.把新的节点的左子树设置成当前节点的左子树
        newNode.left = this.left;
        // 3.把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.right = this.right.left;
        // 4.把当前节点的值替换成右子节点的值
        this.value = this.right.value;
        // 5.把当前节点的右子树设置成右子节点的右子树
        this.right = this.right.right;
        // 6.把当前节点的左子节点设置为新的节点
        this.left = newNode;
    }

    // 右旋转方法
    private void rightRotate() {
        Node node = new Node(value);
        node.right = this.right;
        node.left = this.left.right;
        this.value = this.left.value;
        this.left = this.left.left;
        this.right = node;
    }

    // 查找要删除的节点

    /**
     * @param value 希望要删除的节点的值
     * @return 如果找到返回该节点，否则返回null
     */
    public Node search(int value) {
        if (this.value == value) { // 找到的就是当前节点
            return this;
        } else if (value < this.value) {// 如果查找的值小于当前节点，向左子树递归查找
            // 如果左子节点为空
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {// 查找的值大于等于当前节点，向右子树递归
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }


    // 查找要删除节点的父节点

    /**
     * @param value 要找的节点的值
     * @return 返回的是要删除的节点的父节点，如果没有就返回null
     */
    public Node searchParent(int value) {
        // 如果当前节点就是要删除节点的父节点，就要返回该节点
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)) {
            return this;
        } else {
            // 如果查找的值小于当前节点的值&&当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);// 向左子树递归查找
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);// 向右子树递归查找
            } else {
                return null; // 没有找到父节点
            }
        }
    }

    // 添加节点的方法
    // 递归的形式添加节点，需要满足二叉排序树的要求
    public void add(Node node) {
        if (node == null) {
            return;
        }
        // 判断传入的节点的值和当前子树的根节点的值的关系
        if (node.value < this.value) {
            if (this.left == null) {// 如果当前节点左子节点为空
                this.left = node;
            } else {
                // 递归的向左子树添加
                this.left.add(node);
            }
        } else {// 添加的节点的值大于等于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }

        // 当添加完一个节点后，如果右子树的高度比左子树的高度>1,就要左旋
        if (this.rightHeight() - this.leftHeight() > 1) {
            if (right != null && this.right.leftHeight() > this.right.rightHeight()) {
                this.right.rightRotate();
            }
            leftRotate();// 左旋转
        } else if (this.leftHeight() - this.rightHeight() > 1) {// 当添加完一个节点后 如果左子树的高度比右子树的高度>1，就要右旋转
            // 双旋判断（在满足右旋转的条件下），如果根节点左子树的右子树的高度大于跟节点的左子树的左子树的高度，那么就要
            // 先对左子树进行左旋操作
            if (left != null && left.rightHeight() > left.leftHeight()) {
                // 先对节点的左子树进行左旋操作
                left.leftRotate();
            }
            rightRotate(); // 右旋转
        }

    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}