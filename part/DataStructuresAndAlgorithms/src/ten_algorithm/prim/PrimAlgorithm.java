package ten_algorithm.prim;

import java.util.Arrays;

/**
 * @ClassName PrimAlgorithm
 * @Description 普利姆算法求最小生成树
 * @Author Zhangyuhan
 * @Date 2021/3/24
 * @Version 1.0
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        // 测试看看图是否创建成功
        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verx = data.length;
        // 邻接矩阵的关系使用二维数组描述一下,10000这个大数，表示两个点不连通
        int[][] weight =
                {
                        {10000, 5, 7, 10000, 10000, 10000, 2},
                        {5, 10000, 10000, 9, 10000, 10000, 3},
                        {7, 10000, 10000, 10000, 8, 10000, 10000},
                        {10000, 9, 10000, 10000, 10000, 4, 10000},
                        {10000, 10000, 8, 10000, 10000, 5, 4},
                        {10000, 10000, 10000, 4, 5, 10000, 6},
                        {2, 3, 10000, 10000, 4, 6, 10000},
                };
        // 创建图对象
        MGraph graph = new MGraph(verx);
        // 创建一个最小生成树对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, verx, data, weight);
        // 输出图的邻接矩阵
        // minTree.showGraph(graph);
        // 测试prim算法
        // minTree.prim(graph, 0);// <A,G>2,<G,B>3,<G,E>4,<E,F>5,<F,D>4,<A,C>7
        minTree.prim(graph, 1);// 换一个起始节点，发现最小的权值和仍然是25
    }
}

// 创建最小生成树-》村庄的图
class MinTree {
    // 创建图的邻接矩阵

    /**
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图对应的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int verxs, char[] data, int weight[][]) {
        int i, j;
        for (i = 0; i < verxs; i++) {
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    // 显示图的邻接矩阵方法
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }


    // 编写prim算法，得到最小生成树

    /**
     * @param graph 图
     * @param v     表示从图的第几个顶点开始生成的，'A'->0 || 'B'->'1'...
     */
    // 注意：无论从哪个顶点开始，最小权值和都是相等的
    public void prim(MGraph graph, int v) {
        // visited数组标记顶点是否被访问过
        int[] visited = new int[graph.verxs];
        // visited数组默认元素的值都是0，表示没有访问过
        for (int i = 0; i < graph.verxs; i++) {
            visited[i] = 0;
        }
        // 把当前传进来的节点标记为以访问
        visited[v] = 1;
        // 用h1和h2记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        // 将minWeight初始化为一个大数，后面在遍历过程中，会被替换
        int minWeight = 10000;
        for (int k = 1; k < graph.verxs; k++) {// 因为有graph.verxs个顶点，prim算法结束后，会有graph.verxs-1条边

            // 这个是确定每一次生成的子图中的各个节点，和哪个未被访问过的节点的距离最近
            for (int i = 0; i < graph.verxs; i++) {// i节点表示被访问过的顶点
                for (int j = 0; j < graph.verxs; j++) {// j节点表示还没有访问过的节点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        // 替换minWeight（寻找已经访问过的节点和未访问过的节点间权值最小的边)
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            // 当退出这个for循环时，就找到了一条边是最小的
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">权值是：" + minWeight);
            // 将当前找到的节点标记为已经访问
            visited[h2] = 1;
            // minWeight重新设置为最大值10000
            minWeight = 10000;
        }
    }
}

class MGraph {
    int verxs;// 标识节点图的节点个数
    char[] data;// 保存节点的数据
    int[][] weight;// 存放边，就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}
