package com.geedge.common;


public enum Code {

    SUCCESS(200, "success"),
    ERROR(999, "error");





    private Integer code;
    public Integer getCode() {
        return code;
    }

    private String msg;
    public String getMsg() {
        return msg;
    }


    private Code(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
