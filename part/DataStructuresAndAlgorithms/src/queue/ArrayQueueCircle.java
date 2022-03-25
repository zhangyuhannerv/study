package queue;

/**
 * @ClassName ArrayOueueCircle
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/8
 * @Version 1.0
 */
public class ArrayQueueCircle {
    private int maxSize;//表示数组的最大容量
    private int front;//指向队列的第一个元素，初始值为0
    private int rear;//指向队列的尾部元素的后一个位置，初始值为0
    private int[] arr;//该数组用于存放数据

    //创建队列的构造器
    public ArrayQueueCircle(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        this.front = 0;
        this.rear = 0;
    }

    //判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        //直接将数据加入
        arr[rear] = n;
        //将real后移，这里必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    //获取队列的数据，数据出队列
    public int getQueue() {
        //判断数据是否空
        if (isEmpty()) {
            //通过抛出异常来处理
            throw new RuntimeException("队列空，不能获取数据");
        }
        //需要分析出front指向队列的第一个元素
        //1.先把front对应的值保留到一个临时变量
        //2.将front后移
        //3.将临时保存的变量返回

        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    //显示队列的所有数据
    public void showQueue() {
        //遍历
        if (isEmpty()) {
            System.out.println("队列是空的，没有数据");
            return;
        }
        //从front开始遍历，遍历多少个元素

        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    //求出当前队列有效数据的个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列的头部数据，注意，不是取出数据
    public int hideQueue() {
        //判断
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据");
        }
        return arr[front];
    }
}
