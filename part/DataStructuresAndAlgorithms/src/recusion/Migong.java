package recusion;

/**
 * @ClassName Migong
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/10/25
 * @Version 1.0
 */
public class Migong {
    public static void main(String[] args) {
        // 先创建一个二维数组，模拟迷宫
        // 地图(8行7列）
        int[][] map = new int[8][7];
        // 使用1表示墙
        // 上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        // 左右置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        // 至此，墙全都为1

        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        // 堵死，来观察回溯
//        map[2][2] = 1;
//        map[1][2] = 1;


        // 输出地图
        // 当前地图的情况
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // 使用递归回溯算法给小球找路
//        setWay(map, 1, 1);
        // 使用新的策略 上，右，下，左
        setWay2(map, 1, 1);

        // 输出新的地图，此时就展示小球走过，并标识过的地图
        System.out.println("-----------------------------------");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 使用递归回溯来给小球找路
    // 说明：
    // 1.map表示地图
    // 2.i，j表示从地图的哪个位置开始出发（i，j）
    // 3.如果小球能到达map[6][5]位置，说明通路能够找到
    // 4。约定：当map[i][j]为0时，表示该点没有走过，当为1时表示墙，2表示通路可以走，3表示该点已经走过，但是走不通
    // 5.在走迷宫时，需要先确定一个策略（方法），下-》右-》上-》左，如果该点走不通，再回溯

    /**
     * setWay
     *
     * @param map 表示地图
     * @param i   表示从哪个位置开始找
     * @param j   同i
     * @return 如果找到通路，就返回true，否则，就返回false
     * @author Zhangyuhan
     * @date 2020/10/25 20:18
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) { // 说明通路已经找到
            return true;
        } else {
            if (map[i][j] == 0) {// 说明当前这个点还没有走过
                // 按照这个策略下-》右-》上-》左走
                // 假定该点是可以走通的
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {// 向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {// 向右走
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    // 向上走
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    // 向左走
                    return true;
                } else {
                    // 此时说明该点是走不通的，是死路
                    map[i][j] = 3;
                    // return false，这条路不要走了
                    return false;
                }

            } else { // 如果map[i][j] != 0,此时map[i][j]可能是1，2，3
                // 这里直接返回false，有点难理解
                return false;
            }
        }
    }

    // 修改策略，改成上-》右-》下-》左
    public static boolean setWay2(int[][] map, int i, int j) {
        if (map[6][5] == 2) { // 说明通路已经找到
            return true;
        } else {
            if (map[i][j] == 0) {// 说明当前这个点还没有走过
                // 按照这个策略下-》右-》上-》左走
                // 假定该点是可以走通的
                map[i][j] = 2;
                if (setWay2(map, i - 1, j)) {
                    // 向上走
                    return true;
                } else if (setWay2(map, i, j + 1)) {
                    // 向右走
                    return true;
                } else if (setWay2(map, i + 1, j)) {
                    // 向下走
                    return true;
                } else if (setWay2(map, i, j - 1)) {
                    // 向左走
                    return true;
                } else {
                    // 此时说明该点是走不通的，是死路
                    map[i][j] = 3;
                    // return false，这条路不要走了
                    return false;
                }

            } else { // 如果map[i][j] != 0,此时map[i][j]可能是1，2，3
                // 这里直接返回false，有点难理解o
                return false;
            }
        }
    }
}
