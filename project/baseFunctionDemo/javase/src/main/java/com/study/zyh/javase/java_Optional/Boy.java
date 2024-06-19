package com.study.zyh.javase.java_Optional;

/**
 * @ClassName Boy
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class Boy {
    private Girl girl;

    public Boy() {
    }

    public Boy(Girl girl) {
        this.girl = girl;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }
}
