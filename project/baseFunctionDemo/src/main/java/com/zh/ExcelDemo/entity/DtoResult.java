package com.zh.ExcelDemo.entity;

/**
 * @ClassName DtoResult
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/2
 * @Version 1.0
 */
public class DtoResult<T> {
    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_ERROR = 500;

    private int code;
    private String message;
    private T data;

    public DtoResult() {
        this.setCode(STATUS_CODE_SUCCESS);
    }

    public DtoResult(T data) {
        this();
        this.data = data;
    }

    public DtoResult(Integer code, String message, T data) {
        this.setCode(code);
        this.message = message;
        this.data = data;
    }

    public DtoResult(Integer code, String message) {
        this.setCode(code);
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <T> DtoResult<T> ok() {
        return new DtoResult<T>(STATUS_CODE_SUCCESS, "操作成功", null);
    }

    public static <T> DtoResult<T> ok(T data) {
        return new DtoResult<T>(STATUS_CODE_SUCCESS, "操作成功", data);
    }

    public static <T> DtoResult<T> ok(String message, T data) {
        return new DtoResult<T>(STATUS_CODE_SUCCESS, message, data);
    }

    public static <T> DtoResult<T> error() {
        return new DtoResult<T>(STATUS_CODE_ERROR, "数据异常");
    }

    public static <T> DtoResult<T> error(String message) {
        return new DtoResult<T>(STATUS_CODE_ERROR, message);
    }

    public static <T> DtoResult<T> error(Integer code, String message) {
        return new DtoResult<T>(code, message);
    }

    public T getData() {
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
