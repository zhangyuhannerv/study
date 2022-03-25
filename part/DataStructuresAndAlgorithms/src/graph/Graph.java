package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Graph
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/2/28
 * @Version 1.0
 */
public class Graph {
    public static void main(String[] args) {
        // 测试一把图是否创建ok
       /* int n = 5;// 节点的个数
        String[] vertexs = {"A", "B", "C", "D", "E"};*/

        int n = 8;
        String[] vertexs = {"1", "2", "3", "4", "5", "6", "7", "8"};

        Graph graph = new Graph(n);
        // 循环的添加顶点
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        graph.showGraph();

        // 添加边
        // A-B A-C B-C B-D B-E
        /*graph.insertEdge(0, 1, 1); // A-B
        graph.insertEdge(0, 2, 1); // A-C
        graph.insertEdge(1, 2, 1); // B-C
        graph.insertEdge(1, 3, 1); // B-D
        graph.insertEdge(1, 4, 1); // B-E*/

        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);


        System.out.println("---连接矩阵---");
        graph.showGraph();


        System.out.println("---------------------------");
        System.out.println("--测试dfs(深度优先遍历)--");
        graph.dfs();


        System.out.println();
        System.out.println("---------------------------");
        System.out.println("--测试bfs(广度优先遍历)");
        graph.bfs();
    }

    // 存储顶点集合
    private ArrayList<String> vertexList;
    // 存储图对应的邻接矩阵
    private int[][] edges;
    // 存储边的个数
    private int numsOfEdges;
    // 定义数组boolean[],记录某个节点是否被访问过
    private boolean[] isVisited;


    /**
     * 构造器
     *
     * @param n 表示顶点的个数
     */
    private Graph(int n) {
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        numsOfEdges = 0;
    }

    /**
     * 得到某个节点的第一个链接节点的下标
     *
     * @param index
     * @return 如果存在就返回对应的下标，否则就返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标来获取下一个邻接节点
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }


    /**
     * 深度优先遍历算法
     */
    // 理解i 第一次就是0
    private void dfs(boolean[] isVisited, int i) {
        /*首先访问该节点*/
        System.out.print(getValueByIndex(i) + "->");
        // 将该节点设置为已经访问过
        isVisited[i] = true;
        /*查找i节点的第一个邻接节点w*/
        int w = getFirstNeighbor(i);
        while (w != -1) {// 说明有邻接节点
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            // 如果w的这个节点已经被访问过了
            w = getNextNeighbor(i, w);
        }

    }

    /**
     * 对dfs进行一个重载,遍历所有的节点，并进行dfs
     */
    public void dfs() {
        isVisited = new boolean[getNumsOfVertex()];
        // 遍历所有的节点，进行dfs(回溯)
        for (int i = 0; i < getNumsOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    /**
     * 对一个节点进行广度优先遍历的方法
     */
    private void bfs(boolean[] isVisited, int i) {
        int u;// 表示队列的头节点对应的下标
        int w;// 表示邻接节点的下标
        // 队列，记录节点访问的顺序
        LinkedList queue = new LinkedList();
        // 访问节点，输出节点的信息
        System.out.print(getValueByIndex(i) + "->");
        // 标记为已访问
        isVisited[i] = true;
        queue.addLast(i);
        while (!queue.isEmpty()) {
            // 取出队列的头节点下标
            u = (Integer) queue.removeFirst();
            // 得到第一个邻接点的下标 w
            w = getFirstNeighbor(u);
            while (w != -1) {// 说明找到了
                // 是否访问过
                if (!isVisited[w]) {
                    // 访问
                    System.out.print(getValueByIndex(w) + "->");
                    // 标记为以访问
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }
                // 如果访问过，那么以u为前驱点找w之后的下一个邻接点
                w = getNextNeighbor(u, w);// 此处体现广度优先的思想
            }
        }
    }

    /**
     * 遍历所有的节点，都进行广度优先搜索
     */
    public void bfs() {
        isVisited = new boolean[getNumsOfVertex()];
        for (int i = 0; i < getNumsOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    /**
     * 插入节点
     *
     * @param vertex
     */
    private void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 插入边
     *
     * @param v1     表示点的下标，即是第几个顶点，"A"-"B" "A"->0 "B"->1
     * @param v2     第二个顶点对应的下标
     * @param weight 表示用什么表示两个顶点之间是关联的
     */
    public void insertEdge(int v1, int v2, int weight) {
        // 无向图
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numsOfEdges++;
    }

    /*图中常用的方法：*/

    /**
     * 返回节点的个数
     */
    public int getNumsOfVertex() {
        return vertexList.size();
    }

    /**
     * 返回边的个数
     */
    public int getNumsOfEdges() {
        return numsOfEdges;
    }

    /**
     * 返回节点i对应的值，传入0，返回a;传入1，返回b......
     */
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    /**
     * 返回v1 和 v2 的权值
     */
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    /**
     * 显示图对应的邻接矩阵
     */
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

}
