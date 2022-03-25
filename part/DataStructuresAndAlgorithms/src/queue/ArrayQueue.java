package queue;

/**
 * @ClassName ArrayQueue
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/8
 * @Version 1.0
 */
public class ArrayQueue {
    //目前数组使用一次就不能再使用了，没有达到复用的效果
    //改进的建议：将这个数组使用算法，改进成一个环形的队列：取模
    private int maxSize;//表示数组的最大容量
    private int front;//指向队列的队列头
    private int rear;//指向队列的尾部
    private int[] arr;//该数组用于存放数据

    //创建队列的构造器
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        this.front = -1;//指向队列头部，分析出front指向队列头的前一个位置
        this.rear = -1;//指向队列尾，指向队列尾的数据（即，就是队列的最后一个数据）
    }

    //判断队列是否满
    public boolean isFull() {
        return this.rear == this.maxSize - 1;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n) {
        //判断队列是否满
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        rear++;//让rear后移
        arr[rear] = n;
    }

    //获取队列的数据，数据出队列
    public int getQueue() {
        //判断数据是否空
        if (isEmpty()) {
            //通过抛出异常来处理
            throw new RuntimeException("队列空，不能获取数据");
        }
        front++;//让front后移
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue() {
        //遍历
        if (isEmpty()) {
            System.out.println("队列是空的，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //显示队列的头部数据，注意，不是取出数据
    public int hideQueue() {
        //判断
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据");
        }
        return arr[front + 1];
    }


}
