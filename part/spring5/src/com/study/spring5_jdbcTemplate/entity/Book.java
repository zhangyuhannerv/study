package com.study.spring5_jdbcTemplate.entity;

/**
 * @ClassName Book
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/7
 * @Version 1.0
 */
public class Book {
    private String id;
    private String name;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
