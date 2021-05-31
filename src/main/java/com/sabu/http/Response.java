package com.sabu.http;

public class Response {
    private int code;
    private Object body;

    public Response(int code, Object body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public Object getBody() {
        return body;
    }

}
