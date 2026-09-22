package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 成功，带数据
    public static <E> Result<E> success(E data){
        return new Result<>(0, "操作成功", data);
    }
    //成功，不带数据
    public static Result success(){
        return new Result<>(0, "操作成功", null);
    }
    //失败
    public static Result error(String msg){
        return new Result<>(1, msg, null);
    }
}