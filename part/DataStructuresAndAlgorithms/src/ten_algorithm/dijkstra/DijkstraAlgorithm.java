package ten_algorithm.dijkstra;

import java.net.BindException;
import java.util.Arrays;

/**
 * @ClassName DijkstraAlgorithm
 * @Description 迪杰斯特拉算法解决最短路径问题（单个顶点到其他顶点的最短距离)
 * @Author Zhangyuhan
 * @Date 2021/4/5
 * @Version 1.0
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可链接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        // 创建图对象
        Graph graph = new Graph(vertex, matrix);
        // 看看图的邻接矩阵是否ok
        // graph.showGraph();

       /* graph.dsj(6);
        graph.vv.show();*/

        graph.dsj(2);
        graph.vv.show();
    }

}

class Graph {
    private char[] vertex;// 顶点数组
    private int[][] matrix;// 邻接矩阵
    public VisitedVertex vv;// 表示已经访问的顶点的集合

    // 构造器
    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    // 显示图的方法
    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    // Dijkstra算法
    // index表示出发顶点对应的下标
    public void dsj(int index) {
        this.vv = new VisitedVertex(vertex.length, index);
        update(index); // 更新index下标顶点到周围顶点的距离和前驱顶点
        for (int i = 1; i < vertex.length; i++) {
            index = vv.updateArr(); // 选择并返回新的访问顶点
            update(index);// 更新index下标顶点到周围顶点的距离和前驱顶点
        }
    }

    // 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int len = 0;
        // 遍历我们的邻接矩阵matrix[index]行
        for (int i = 0; i < matrix[index].length; i++) {
            // 出发顶点到index的距离+从index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][i];
            if (!vv.in(i) && len < vv.getDis(i)) {// 如果j顶点没有被访问过，并且len小于出发顶点到j顶点的距离，就需要更新
                vv.updatePre(i, index); // 更新j顶点的前驱顶点为index顶点
                vv.updateDis(i, len);// 更新出发顶点到j顶点的距离
            }
        }
    }
}

class VisitedVertex {
    public int[] already_arr; // 记录每个顶点是否访问过，1表示访问过，0表示未访问，会动态更新
    public int[] pre_visited;// 每个下标对应的值为前一个的顶点下标，会动态更新
    public int[] dis;// 记录出发顶点到其他所有顶点的距离。比如G为起始顶点，就会记录G到其他顶点的距离，会动态更新，求的最短距离就会放到dis里


    // length表示顶点的个数
    // index表示出发顶点对应的下标，比如G顶点的下标就是6
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];

        // 初始化dis的数组
        Arrays.fill(dis, 65535);
        this.already_arr[index] = 1; // 设置出发顶点被访问过
        this.dis[index] = 0;// 设置出发顶点的访问距离为0
    }

    // 判断index顶点是否被访问过
    // 如果访问过就返回true,否则就返回false
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    // 更新出发顶点到index顶点的距离
    public void updateDis(int index, int len) {
        this.dis[index] = len;
    }

    // 更新下标为pre的顶点的前驱顶点为index顶点
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    // 返回出发顶点到index顶点的距离
    public int getDis(int index) {
        return dis[index];
    }

    // 显示最后的结果
    public void show() {
        System.out.println("===========================");
        System.out.println(Arrays.toString(already_arr));
        System.out.println(Arrays.toString(pre_visited));
        System.out.println(Arrays.toString(dis));
        // 处理一下dis
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ")");
            } else {
                System.out.print("N");
            }
            count++;
        }
    }

    // 继续选择并返回新的访问节点，比如这里的G完成后，就是A作为新的访问顶点（注意不是出发顶点）
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        already_arr[index] = 1;
        return index;

    }
}






















