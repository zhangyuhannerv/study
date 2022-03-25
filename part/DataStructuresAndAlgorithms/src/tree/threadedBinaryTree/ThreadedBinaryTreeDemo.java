package tree.threadedBinaryTree;

/**
 * @ClassName ThreadedBinaryTreeDemo
 * @Description 线索化二叉树
 * @Author Zhangyuhan
 * @Date 2020/11/29
 * @Version 1.0
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        // 测试一把中序线索二叉树的一个功能
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        //[8,3,10,1,14,6]

        // 二叉树后面要递归创建，现在暂时手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        // 测试线索化
        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree();
        threadBinaryTree.setRoot(root);
        threadBinaryTree.threadedNodes();

        // 测试中序线索化是否正确
        // 以编号为10的节点node5做测试
        System.out.println(node5.getLeft()); // 应该是node2：3,jack
        System.out.println(node5.getRight()); // 应该是node1：1,tom

        // 当线索化二叉树后，不能再使原来的遍历方法（会报栈溢出异常）
        // threadBinaryTree.infixOrder();


        System.out.println("使用线索化的方式遍历线索化二叉树如下：");
        threadBinaryTree.threadedList();// 要求顺序：8，3，10，1，14，6
    }
}


// 实现了线索化功能的二叉树
class ThreadBinaryTree {
    private HeroNode root;
    // 为了实现线索化，需要创建一个指向当前节点的前驱节点的指针
    // 在递归进行线索化时，pre总是保留前一个节点
    private HeroNode pre = null;

    // 根节点的set方法
    public void setRoot(HeroNode heroNode) {
        this.root = heroNode;
    }

    public HeroNode getRoot() {
        return root;
    }

    public HeroNode getPre() {
        return pre;
    }

    public void setPre(HeroNode pre) {
        this.pre = pre;
    }

    //===========================================================================================================
    //中序遍历线索化二叉树的方法（中序线索化二叉树是分别有前序，中序，后序的遍历方法的,下面是中序遍历的方法）
    public void threadedList() {
        // 定义一个变量，存储当前遍历的节点，遍历从root开始
        HeroNode node = root;
        while (node != null) {
            // 循环找到leftType为1的节点，第一个找到的就是原先中序遍历的第一个节点
            // node后面随着遍历而变化，当left为1时，说明该节点是线索化处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }
            // 打印当前这个节点
            System.out.println(node);
            // 如果当前节点的右指针指向的是后续节点
            while (node.getRightType() == 1) {
                // 就获取到当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }
            // 此时node.getRightType == 0
            // 替换这个遍历的节点
            node = node.getRight();
        }

    }

// ----------------------------------------------------------------
    // 线索化的核心方法
    // 编写对二叉树进行中序线索化的方法

    // 重载一下线索化二叉树的方法
    public void threadedNodes() {
        this.threadedNodes(root);
    }

    /**
     * @param node 就是当前需要线索化的节点
     */
    public void threadedNodes(HeroNode node) {
        // 如果node == null 就无法线索化
        if (node == null) {
            return;
        }

        // 中序线索化
        /**
         * 1.先线索化左子树
         */
        threadedNodes(node.getLeft());

        /** 2.线索化当前节点(有点难度)
         */
        // 处理当前节点的前驱节点
        // 以8节点来理解的话
        // 8节点的left应该指向空，8的leftType为1
        if (node.getLeft() == null) {
            // 就让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型
            node.setLeftType(1);// 当前节点的左指针指向的是前驱节点
        }
        // 处理后继节点
        if (pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            // 修改前驱节点的右指针类型
            pre.setRightType(1);
        }

        // ！！！一定要加上这句话，，每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;


        /**
         * 3.再线索化右子树
         */
        threadedNodes(node.getRight());
    }

    //-----------------------------------------------------------------------


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


// 创建HeroNode
class HeroNode {
    private int no;
    private String name;
    private HeroNode left; // 默认null
    private HeroNode right; // 默认为null

    /**
     * 说明：
     * 1.如果leftTyPe==0表示它指向的是左子树
     * 2.如果是1表示它指向的是前驱节点
     */
    private int leftType; // 初始值是0

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    /**
     * 说明：
     * 1.如果leftTyPe==0表示它指向的是右子树
     * 2.如果是1表示它指向的是后继节点
     */
    private int rightType; // 初始值是0

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }


    public HeroNode(int leftType, int rightType) {
        this.leftType = leftType;
        this.rightType = rightType;
    }

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

