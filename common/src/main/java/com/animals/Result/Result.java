package com.animals.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>
{
    private Integer code;
    private String msg;
    private T data;

    public static Result success()
    {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static <T> Result<T> success(T object)
    {
        Result result = new Result();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result error(String msg)
    {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
