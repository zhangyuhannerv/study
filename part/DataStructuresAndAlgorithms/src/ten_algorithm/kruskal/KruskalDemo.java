package ten_algorithm.kruskal;

import java.util.Arrays;

/**
 * @ClassName KruskalDemo
 * @Description 克鲁斯卡尔解决最小生成树问题
 * @Author Zhangyuhan
 * @Date 2021/3/31
 * @Version 1.0
 */
public class KruskalDemo {
    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};
        // 创建kruskalCase对象实例
        KruskalDemo kruskalDemo = new KruskalDemo(vertexs, matrix);
        // 输出构建的是否正确
        // kruskalDemo.print();

        // EData[] edges = kruskalDemo.getEdges();
        // System.out.println("排序前");
        // System.out.println(Arrays.toString(edges));

        // System.out.println("排序后");
        // kruskalDemo.sortEdges(edges);
        // System.out.println(Arrays.toString(edges));

        kruskalDemo.kruskal();


    }


    private int edgeNum;// 边的个数
    private char[] vertex;// 顶点数组
    private int[][] matrix;// 邻接矩阵
    private static final int INF = Integer.MAX_VALUE;// 使用INF来表示两个顶点不能联通

    // 构造器
    public KruskalDemo(char[] vertex, int[][] matrix) {
        // 初始化定点数和边的个数
        int vlen = vertex.length;
        // 初始化顶点,用的复制拷贝的方式
        this.vertex = new char[vlen];
        for (int i = 0; i < vlen; i++) {
            this.vertex[i] = vertex[i];
        }

        // 初始化边，使用的复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        // 统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    // 打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为");
        for (int i = 0; i < vertex.length; i++) {
            for (int j = 0; j < vertex.length; j++) {
                System.out.printf("%12d", matrix[i][j]);
            }
            System.out.println();// 换行
        }
    }

    // 对边进行排序处理，冒泡排序(从小到大)
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {// 交换
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    // 传入一个顶点的值，返回该顶点对应的下标
    // 比如传入'B'，返回1，如果找不到，返回-1
    private int getPosition(char ch) {
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    // 获取图中的边，放到EData[]数组中，后面需要遍历该数组
    // 是通过邻接矩阵matrix来获取
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertex.length; i++) {
            for (int j = i + 1; j < vertex.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertex[i], vertex[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    // 功能:获取下标为i的顶点的终点，用于判断两个顶点的终点是否相同
    // ends数组记录了各个顶点对应的终点是哪个，ends数组是在遍历过程中，逐步形成的
    // i表示传入的顶点对应的下标
    // 返回的就是下标为i的这个顶点对应的终点的下标
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

    // kruskal核心算法
    public void kruskal() {
        int index = 0;// 表示最后结果数组的索引
        int[] ends = new int[edgeNum];// 用于保存以有最小生成树中的每个顶点在最小生成树中的终点
        // 创建结果数组，保存最后的最小生成树
        EData[] rets = new EData[edgeNum];
        // 获取图中所有的边的集合
        EData[] edges = getEdges();
        // 按照边的权值从小到大进行排序
        sortEdges(edges);
        // 遍历edges数组,如果不构成回路，那么就将边放入到最小生成树中
        for (int i = 0; i < edges.length; i++) {
            // 获取到第i条边的第一个顶点的下标
            int p1 = getPosition(edges[i].start);
            // 获取到第i跳变的第二个顶点的下标
            int p2 = getPosition(edges[i].end);

            // 获取p1这个顶点它在已有的最小生成树的终点
            int m = getEnd(ends, p1);
            // 获取p2这个顶点它在已有的最小生成树的终点
            int n = getEnd(ends, p2);

            //判断是否会构成回路
            if (m != n) {// 没有构成回路
                ends[m] = n;// 设置m在已有的最小生成树中的终点
                rets[index++] = edges[i];// 有一条边加入到rets数组中去
            }
        }

        // 统计并打印最小生成树
        System.out.println("最小生成树是");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }
    }
}

// 创建一个边类EData,它的对象实例就表示一条边
class EData {
    char start;// 边的一个点
    char end;// 边的另外一个点
    int weight; // 边的权值

    // 构造器
    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    // 重写toString方法，便于输出这一条边

    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }


}


































