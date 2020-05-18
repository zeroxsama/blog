package cn.cjun.blog.entity;

import lombok.Data;

@Data
public class Result {

    private Boolean flag;
    private String message;
    private Object data;


    public Result(Boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}