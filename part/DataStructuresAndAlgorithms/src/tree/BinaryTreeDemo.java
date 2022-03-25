package tree;

/**
 * @ClassName BinaryTreeDemo
 * @Description 树的前序，中序，后序遍历，与查找
 * @Author Zhangyuhan
 * @Date 2020/11/18
 * @Version 1.0
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        // 现需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        // 创建需要的节点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        // 说明：
        /**
         * 我们现在先手动创建二叉树，后面我们学习递归的方式创建二叉树
         */
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);

        // 测试前序遍历
        System.out.println("前序遍历");// 1,2,3,5,4
        binaryTree.preOrder();

        // 测试中序遍历
        System.out.println("中序遍历");// 2,1,5,3,4
        binaryTree.infixOrder();

        // 测试后序遍历
        System.out.println("后序遍历");// 2,5,4,3,1
        binaryTree.postOrder();

        // 前序查找
        // 前序查找比较的次数：4
        System.out.println("前序查找:");
        HeroNode resNode = binaryTree.preOrderSearch(5);
        if (resNode != null) {
            System.out.println("前序查找找到了,信息为：" + resNode.toString());
        } else {
            System.out.printf("前序查找没有找到%d的英雄", 5);
        }

        // 中序查找
        // 中序查找比较的次数：3
        System.out.println("中序查找:");
        HeroNode resNode1 = binaryTree.infixOrderSearch(5);
        if (resNode1 != null) {
            System.out.println("中序查找找到了,信息为：" + resNode1.toString());
        } else {
            System.out.printf("中序查找没有找到%d的英雄", 5);
        }

        // 后序查找
        // 后序查找比较的次数：2
        System.out.println("后序查找:");
        HeroNode resNode2 = binaryTree.postOrderSearch(5);
        if (resNode2 != null) {
            System.out.println("后序查找找到了,信息为：" + resNode2.toString());
        } else {
            System.out.printf("后序查找没有找到%d的英雄", 5);
        }

        // 测试一把删除节点的代码，此时注释掉上面除了生成树以外的代码
        System.out.println("删除前，前序遍历");
        binaryTree.preOrder(); // 1,2,3,5,4
        binaryTree.delNode(5);
        // binaryTree.delNode(3);
        System.out.println("删除后，前序遍历");
        binaryTree.preOrder(); // 1,2,3,4 || 1,2
    }
}

// 定义一个BinaryTree(二叉树）
class BinaryTree {
    private HeroNode root;

    // 根节点的set方法
    public void setRoot(HeroNode heroNode) {
        this.root = heroNode;
    }

    // 树前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    // 树中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    // 树后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    // 前序遍历查找
    public HeroNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    // 中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        if (root != null) {
            return root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    // 后序遍历查找
    public HeroNode postOrderSearch(int no) {
        if (root != null) {
            return root.postOrderSearch(no);
        } else {
            return null;
        }
    }

    // 删除节点
    public void delNode(int no) {
        if (root != null) {
            // 如果只有一个root节点，这里就要判断root是否是要删除的节点
            if (root.getNo() == no) {
                root = null;
            } else {
                // 递归删除
                root.delNode(no);
            }
        } else {
            System.out.println("空树，无法删除");
        }
    }
}

// 先创建HeroNode节点
class HeroNode {
    private int no;
    private String name;
    private HeroNode left; // 默认null
    private HeroNode right; // 默认为null

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 编写前序遍历的方法
    public void preOrder() {
        System.out.println(this); // 先输出父节点,默认调用toString方法
        // 递归调用左子树
        if (this.left != null) {
            this.left.preOrder();
        }
        // 递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    // 中序遍历
    public void infixOrder() {
        // 递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        // 输出当前节点（也就是父节点）
        System.out.println(this);
        // 递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }

    }

    // 后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    // 前序遍历查找

    /**
     * @param no 查找no
     * @return 如果找到就返回该node，如果没有找到就返回null
     */
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }
        // 判断当前节点的左子节点是否为空,如果不为空，则递归前序查找
        // 如果左递归找到就要返回找到的节点
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null) {// 说明左递归找到了
            return resNode;
        }

        // 如果左递归没有找到，接继续右递归
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        // 此时不管找没找到都要返回resNode
        return resNode;
    }

    // 中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        HeroNode heroNode = null;
        // 左递归
        if (this.left != null) {
            heroNode = this.left.infixOrderSearch(no);
        }
        if (heroNode != null) {
            return heroNode;
        }

        System.out.println("进入中序查找");
        // 查找当前
        if (this.no == no) {
            return this;
        }

        // 右递归
        if (this.right != null) {
            heroNode = this.right.infixOrderSearch(no);
        }

        return heroNode;
    }

    // 后序遍历查找
    public HeroNode postOrderSearch(int no) {
        HeroNode heroNode = null;
        // 左递归
        if (this.left != null) {
            heroNode = this.left.postOrderSearch(no);
        }
        if (heroNode != null) {
            return heroNode;
        }

        // 右递归
        if (this.right != null) {
            heroNode = this.right.postOrderSearch(no);
        }

        if (heroNode != null) {
            return heroNode;
        }

        System.out.println("进入后序查找");
        // 如果左右子树都没有找到,就比较当前节点
        if (this.no == no) {
            return this;
        }

        return heroNode;
    }

    // 递归删除节点

    /**
     * 如果删除的是叶子节点，就删除该节点
     * 如果是非叶子节点，就删除该子树
     */
    public void delNode(int no) {
        // 判断左子节点是否为要找的节点
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }
        // 判断右子节点是否为要找的节点
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }
        // 向左子树遍历
        if (this.left != null) {
            this.left.delNode(no);
            // 注意这里不要写return，下面同理
        }
        // 向右子树遍历
        if (this.right != null) {
            this.right.delNode(no);
        }

    }
}



