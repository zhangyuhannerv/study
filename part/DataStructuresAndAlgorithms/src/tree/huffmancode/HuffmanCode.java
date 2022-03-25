package tree.huffmancode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.CollationElementIterator;
import java.util.*;

/**
 * @ClassName HuffmanCode
 * @Description 霍夫曼编码
 * @Author Zhangyuhan
 * @Date 2021/2/5
 * @Version 1.0
 */
public class HuffmanCode {
    public static void main(String[] args) {
        /*char a = 'a';
        // byte codeNum = 97;
        int codeNum = 97;
        System.out.println(a == codeNum); // 无论是byte还是int，输出是true，这说明字符char本身就是一个数字*/

        String conent = "i like like like java do you like a java";
        byte[] contentBytes = conent.getBytes();// 数组的长度是40
        System.out.println(Arrays.toString(contentBytes));
        System.out.println("-----------------------------");
      /*  List<Node> nodes = getNodes(contentBytes);// [Node{data=32, weight=9}, Node{data=97, weight=5}, Node{data=100, weight=1}........]
        // 测试一把创建霍夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        // preOrder(huffmanTreeRoot);
        // 测试一把是否生成了对应霍夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        // System.out.println("生成的霍夫曼编码表是:" + huffmanCodes);
        byte[] zip = zip(contentBytes, huffmanCodes);// 数组的长度是17，压缩率23/40 = 57.5%*/

        // 数据的压缩
        byte[] huffmanCodesBytes = huffmanZip(contentBytes);
        System.out.println(Arrays.toString(huffmanCodesBytes));// 数组的长度是17，压缩率23/40 = 57.5%*/
        System.out.println("-----------------------------");
        // 然后就可以发送压缩后的字节数组了~~~~~~

        // 数据的解压
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes);
        System.out.println(Arrays.toString(sourceBytes));
        System.out.println("-----------------------------");
        System.out.println(new String(sourceBytes));

        // 测试文件的压缩
        // zipFile("D://wendang//图片//头像//头像.jpg", "C://Users//13551//Desktop//file.zip");
        // System.out.println("压缩文件ok");
        // 测试文件解压
        // unZipFile("C://Users//13551//Desktop//file.zip", "C://Users//13551//Desktop//头像.jpg");
        // System.out.println("解压文件ok");

        // 单个文件的压缩和解压都ok,以后可以用自己写的代码来压缩和解压文件了o(*￣▽￣*)o
    }

    // 编写一个方法，将一个文件进行压缩

    /**
     * zipFile
     *
     * @param srcFile 希望压缩的文件的完整路径
     * @param dstFile 将压缩文件放到哪个目录下
     * @return
     * @author Zhangyuhan
     * @date 2021/2/13 22:43
     */
    public static void zipFile(String srcFile, String dstFile) {
        // 创建输入流与输出流
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            // 创建文件的输入流
            is = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的byte数组
            byte[] b = new byte[is.available()];
            is.read(b);
            // 对文件进行压缩
            byte[] huffmanBytes = huffmanZip(b);
            // 创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            // 创建一个和文件输出流关联的ObjectOutPutStream
            oos = new ObjectOutputStream(os);
            // 这里我们已对象流的方式写入霍夫曼的编码，是为了以后我们恢复原文件时使用
            oos.writeObject(huffmanBytes);// 先把压缩文件的字节数组写入
            oos.writeObject(huffmanCodes);// 同时要把霍夫曼编码写入压缩文件，不然回复不了
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 编写一个方法，完成对压缩文件的解压

    /**
     * unZipFile
     *
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     * @return
     * @author Zhangyuhan
     * @date 2021/2/14 0:04
     */
    public static void unZipFile(String zipFile, String dstFile) {
        // 定义文件的输入流
        InputStream is = null;
        // 定义一个对象输入流
        ObjectInputStream ois = null;
        // 定义一个文件的输出流
        OutputStream os = null;
        try {
            // 创建文件输入流
            is = new FileInputStream(zipFile);
            // 创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            // 读取byte数组
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 读取huffman编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            // 解码
            byte[] sourceByte = decode(huffmanCodes, huffmanBytes);
            // 创建文件输出流
            os = new FileOutputStream(dstFile);
            // 数据写出
            os.write(sourceByte);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 完成数据的解压
    // 思路
    // 1.将霍夫曼字节数组重写转成对应的二进制的字符串“101000110..."
    // 2.将二进制字符串对应霍夫曼编码=>‘i like like java.....’

    /**
     * byteToBitString  将一个byte转成二进制的字符串
     *
     * @param flag 表示标识是否需要补高位，如果是true，表示需要补高位，如果是false，表示不补
     * @return 是该字节b对应的二进制的字符串（注意是按照补码返回的），但是没关系，因为我们压缩的时候也是按照补码编译的
     * @author Zhangyuhan
     * @date 2021/2/13 21:18
     */
    private static String byteToBitString(boolean flag, byte b) {
        // 使用变量保存b
        int temp = b;// 将b转成int
        if (flag) {
            // 正数需要补位
            temp |= 256;// 按位与（256=> 1 0000 0000)
        }

        String toBinaryString = Integer.toBinaryString(temp); // 返回的是temp对应的二进制的补码

        if (flag || temp < 0) { // 这里如果最后一个字节是负数，那么也需要截取
            // 需要截取最后的8位
            return toBinaryString.substring(toBinaryString.length() - 8);
        } else {
            return toBinaryString;
        }
    }

    // 编写一个方法完成对压缩数据的解码

    /**
     * decode
     *
     * @param huffmanCodes 霍夫曼编码表
     * @param huffmanBytes 霍夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     * @author Zhangyuhan
     * @date 2021/2/13 21:38
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        // 1.先得到huffmanBytes对应的二进制的字符串
        StringBuilder stringBuilder = new StringBuilder();
        // 2.将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            boolean flag = (i != huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(flag, huffmanBytes[i]));
        }
        // 把字符串按照指定的霍夫曼编码进行解码
        // 把霍夫曼编码表进行调换，因为要反向查询97->100 || 100->97
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        // 创建一个集合，存放byte
        List<Byte> list = new ArrayList<>();
        // i可以理解成就是一个索引，它在不停的扫描StringBuilder
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;// 小的计数器
            boolean flag = true;
            Byte b = null;
            while (flag) {
                // 取出一个‘1’或者是'0'
                // 递增地取出key
                String key = stringBuilder.substring(i, i + count);// i不动，让count移动，直到匹配到一个字符
                b = map.get(key);
                if (b == null) {// 说明没有匹配到
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;// 直接让i移动到count地位置
        }

        // 当for循环结束后，list中就存放了所有的字符
        // 把list中的数据放入byte[]中并返回
        byte[] byteArr = new byte[list.size()];
        for (int i = 0; i < byteArr.length; i++) {
            byteArr[i] = list.get(i);
        }
        return byteArr;
    }


    // 使用一个方法，将所有的方法都封装起来，便于调用

    /**
     * huffmanZip
     *
     * @param bytes 原始的字符串对应的字节数组
     * @return 经过huffman编码处理后的字节数组（压缩后的数组）
     * @author Zhangyuhan
     * @date 2021/2/13 1:43
     */
    public static byte[] huffmanZip(byte[] bytes) {
        // 将字节数组转为节点集合
        List<Node> nodes = getNodes(bytes);
        // 创建huffmanTree
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        // 根据huffmanTree生成huffmanCodes(霍夫曼编码)
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        // 根据生成的霍夫曼编码压缩得到霍夫曼编码字节数组
        byte[] zip = zip(bytes, huffmanCodes);
        // 返回压缩后的字节数组
        return zip;
    }

    // 编写一个方法，将一个字符串对应的byte数组，通过生成的huffman编码表返回一个huffman编码压缩后的byte数组

    /**
     * zip
     *
     * @param bytes        这是原始的字符串对应的byte数组
     * @param huffmanCodes 生成的huffman编码Map
     * @return 返回 霍夫曼编码处理后的byte数组
     * @author Zhangyuhan
     * @date 2021/2/13 0:48
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 1.先利用huffmanCodes编码表将bytes转成huffman编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        // 2.遍历byte数组
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        // 3.将2进制字符串转为byte[]数组
        int len = (stringBuilder.length() + 7) / 8;
        // 4.创建存储压缩后的byte数组
        byte[] by = new byte[len];
        int index = 0; // 记录是第几个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) {// 因为是每8位对应一个byte，所以步长应该是+8
            String strByte;
            if (i + 8 > stringBuilder.length()) {// 最后一次截取不够8位
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            // 将strByte，转成一个byte,放入奥by[]数组中
            by[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        return by;
    }

    // 生成的赫夫曼树对应的赫夫曼编码
    // 思路分析
    // 1.将赫夫曼编码表存放在map中 Map<byte，String> 形式 32->01 97->100 100->11000......
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 2.在生成赫夫曼编码表时需要不停的去拼接路径，所以要定义一个StringBuilder来存储某个叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * getCodes
     * 功能：将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param node          传入节点
     * @param code          路径：左子节点是0，右子节点是1
     * @param stringBuilder 是用于拼接路径的
     * @return
     * @author Zhangyuhan
     * @date 2021/2/13 0:24
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        // 将code加入到StringBuilder2
        stringBuilder2.append(code);
        if (node != null) {// 如果node == null那么不处理
            // 判断当前node是叶子节点还是非叶子节点
            if (node.data == null) { // 说明该节点是非叶子节点
                // 递归处理
                // 向左递归
                getCodes(node.left, "0", stringBuilder2);
                // 向有递归
                getCodes(node.right, "1", stringBuilder2);
            } else {// 说明是一个叶子节点
                // 就表示找到了某个叶子节点的最后
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    // 为了调用方便，重载getCodes方法
    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        // 处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        // 处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }

    // 前序遍历的方法
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("霍夫曼树为空，不能遍历");
        }
    }

    /**
     * getNodes
     *
     * @param bytes 接收的字节数组
     * @return 返回的就是list，形式：Node[data='97',weight=5]
     * @author Zhangyuhan
     * @date 2021/2/6 8:58
     */

    private static List<Node> getNodes(byte[] bytes) {
        // 1.创建一个ArrayList;
        ArrayList<Node> nodes = new ArrayList<>();

        // 2.遍历bytes,统计每个byte出现的次数 =>map
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {// counts还没有b这个字符数据
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 3.把每个键->值对转成一个node对象,并加入到nodes中
        // 遍历map
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            nodes.add(node);
        }
        return nodes;
    }

    // 通过对应的节点list，创建对应的霍夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 排序，从小到大
            Collections.sort(nodes);
            // 取出第一棵最小的二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            // 创建一颗新的二叉树，它的根节点，没有data，只有weight
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            // 将已经处理的两颗二叉树从nodes移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 将新的二叉树，加入到nodes
            nodes.add(parent);
        }
        // nodes最后里面只有一个节点，就是霍夫曼树里面的根节点
        return nodes.get(0);

    }

}

// 创建Node，带数据和权值
class Node implements Comparable<Node> {
    Byte data; // 存放数据本身，比如'a'字符=>ascii码=>97
    int weight; // 权值，表示字符出现的次数
    Node left;
    Node right;

    public Node() {
    }

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    // 前序遍历
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
