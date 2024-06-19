package com.study.zyh.javase.java_io;

/**
 * @ClassName _9Reader
 * @Description 处理流（包装流）的修饰器设计模式的简单理解
 * @Author Zhangyuhan
 * @Date 2021/9/15
 * @Version 1.0
 */
public abstract class _9Reader {//抽象类

    public void readFile() {
    }

    public void readString() {
    }

    // 在抽象类，也可以使用read()方法统一管理
    // 在后面调用时，利用对象的动态绑定机制，绑定到对应的实现子类即可
    public void read_() {

    }
}

// 如果想单独看例子，解开下面的例子即可
// 否则会对jdk里面的源码有影响

/*
// 节点流1
class FileReader extends _9Reader {
    public void readFile() {
        System.out.println("读取文件");
    }
}


// 节点流2
class StringReader extends _9Reader {
    public void readString() {
        System.out.println("读取字符串");
    }
}

// 处理流
class BufferedReader extends _9Reader {
    private final _9Reader reader;// 属性是_9Reader的实例

    public BufferedReader(_9Reader reader) {
        this.reader = reader;
    }

    // 重写父类的方法的readFile()
    public void readFile() {
        this.reader.readFile();
    }


    // 让方法更加灵活,多次读取文件,或者加缓冲
    public void readFiles(int num) {
        for (int i = 0; i < num; i++) {
            reader.readFile();
        }
    }

    // 扩展方法，批量处理字符串数据
    public void readStrings(int num) {
        for (int i = 0; i < num; i++) {
            reader.readString();
        }
    }
}

class Test {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new FileReader());
        bufferedReader.readFile();// 此时走的是字类的读取文件的方法，会打印出结果的，因为包装类重写了父类的readFile()方法
        bufferedReader.readFiles(2);
        bufferedReader.readStrings(2); // 这里走父类的readString()方法

        BufferedReader bufferedReader2 = new BufferedReader(new StringReader());
        bufferedReader2.readFiles(2); // 这里走父类的readFile()方法
        bufferedReader2.readStrings(2);
    }
}
*/








