package com.zh.BaiDuPageDemo;

/**
 * @ClassName BaiDuPageDemo
 * @Description 用java代码实现百度的分页效果
 * @Author Zhangyuhan
 * @Date 2021/7/30
 * @Version 1.0
 */
public class BaiDuPageDemo {
    public static void main(String[] args) {

        // 定义当前页(变化)
        int currentPage = 12;
        // 定义总页数（总数据/每页条数)(变化)
        int totalPage = 19;

        showPage(currentPage, totalPage);
    }

    // 在控制台上打印百度的分页栏
    private static void showPage(int currentPage, int totalPage) {
        // 定义开始
        int begin = 1;
        // 定义结束
        int end = 10;

        // 如果总页数小于等于10，让begin = 1,end = 总页数
        if (totalPage <= 10) {
            begin = 1;
            end = totalPage;
        }

        // 如果总页数大于10
        if (totalPage > 10) {
            begin = currentPage - 5;
            end = currentPage + 4;

            // 对begin做负数处理
            if (begin <= 0) {
                begin = 1;
                end = 10;
            }

            // 对end超过totalPage做处理
            if (end > totalPage) {
                begin = totalPage - 9;// 这里注意要减9而不是减10，因为下面的遍历是'='和'<='
                end = totalPage;
            }
        }

        // 要给begin和end赋值

        // 如果当前页>1，那么显示上一页
        if (currentPage > 1) {
            System.out.print("上一页\t");
        }

        for (int i = begin; i <= end; i++) {
            System.out.print(i + "\t");
        }

        // 当前页小于总页数，应该显示下一页
        if (currentPage < totalPage) {
            System.out.print("下一页\t");
        }
    }
}
