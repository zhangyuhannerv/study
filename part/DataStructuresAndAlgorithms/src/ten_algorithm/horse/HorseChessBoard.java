package ten_algorithm.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * @ClassName HorseChessBoard
 * @Description 骑士周游回溯算法（贪心算法优化)
 * @Author Zhangyuhan
 * @Date 2021/4/5
 * @Version 1.0
 */
public class HorseChessBoard {
    public static void main(String[] args) {
        // 测试算法是否正确
        X = 8;
        Y = 8;
        int row = 1;// 马儿初始位置的行，从1开始编号
        int column = 1;// 马儿的初始位置的列，从1开始编号
        // 创建棋盘
        int[][] chessBoard = new int[X][Y];
        visited = new boolean[X * Y];// 初始值都是false

        // 测试花费时间
        long start = System.currentTimeMillis();
        travelChessBoard(chessBoard, row - 1, column - 1, 1);
        long end = System.currentTimeMillis();
        System.out.println("共耗时:" + (end - start) + "ms");
        System.out.println("输出棋盘为:");
        for (int[] rows : chessBoard) {
            for (int col : rows) {
                System.out.print(col + "\t");
            }
            System.out.println();
        }
    }

    private static int X;// 表示棋盘的列数
    private static int Y;// 表示棋盘的行数
    // 创建一个数组来标记棋盘的各个位置是否被访问过
    private static boolean visited[];
    // 创建一个属性来标记是否棋盘的所有位置都被访问过了
    private static boolean finished;// 如果为true表示成功

    // 根据当前的位置（point对象来描述的),计算马儿还能走哪些位置，并放入到一个集合中，最多有8个位置
    public static List<Point> next(Point curPoint) {
        // 创建一个ArrayList
        List<Point> ps = new ArrayList<Point>();
        Point p1 = new Point();
        // 表示马可以走左上偏下的位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        // 下边是判断马能否走其他的七个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        return ps;
    }

    // 核心算法

    /**
     * travelChessBoard
     * <p>
     * 完成骑士周游回溯的核心算法
     *
     * @param chessboard 棋盘
     * @param row        马儿当前的位置的行，从0开始
     * @param colum      马儿当前的位置的列，从0开始
     * @param step       是第几步，初始位置就是第一步
     * @return
     * @author Zhangyuhan
     * @date 2021/4/5 22:55
     */
    public static void travelChessBoard(int[][] chessboard, int row, int colum, int step) {
        chessboard[row][colum] = step;
        visited[row * X + colum] = true;// 标记该位置已经访问
        // 获取当前位置可以走的下一个位置的集合
        List<Point> ps = next(new Point(colum, row));

        // 使用贪心算法对原来的算法进行优化
        // 获取到了下一个位置的集合ps
        // 我们需要对ps中的所有的point下一步的所有集合个数进行非递减排序（1，2，2，3，4，5，5，5，5，6.....）
        sort(ps);

        // 遍历我们的ps
        while (!ps.isEmpty()) {
            Point p = ps.remove(0); // 取出下一个可以走的位置
            // 判断该点是否已经访问过
            if (!visited[p.y * X + p.x]) {// 说明没有访问过
                travelChessBoard(chessboard, p.y, p.x, step + 1);
            }
        }
        // 判断马儿是否完成了任务
        // step<X*Y的情况有两种:棋盘到目前位置任然没有走完;棋盘走完了，目前处于回溯状态
        if (step < X * Y && !finished) {// 如果没有完成任务
            chessboard[row][colum] = 0;
            visited[row * X + colum] = false;
        } else {
            finished = true;
        }
    }


    // 根据当前这一步的所有的下一步的下一步个数，进行非递减排序,减少回溯的动作
    public static void sort(List<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                // 获取O1点的下一步的所有位置的个数
                List<Point> next1 = next(o1);
                List<Point> next2 = next(o2);
                if (next1.size() < next2.size()) {
                    return -1;
                } else if (next1.size() == next2.size()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

}
