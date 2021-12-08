package com.lxj.shardingjdbc.controller;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
public class Response {
    private int code;
    private String message;

    public static Response success(){
        return new Response(0, "SUCCESS");
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
