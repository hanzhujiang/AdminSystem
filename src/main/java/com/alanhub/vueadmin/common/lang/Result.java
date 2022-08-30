package com.alanhub.vueadmin.common.lang;


import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return  Result.succ(200,"success",data);
    }
    public static Result succ(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
