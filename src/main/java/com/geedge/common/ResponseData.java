package com.geedge.common;

import cn.hutool.json.JSONObject;

import java.util.HashMap;


public class ResponseData extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;


    public ResponseData() {
        put("code", Code.SUCCESS.getCode());
        put("msg", Code.SUCCESS.getMsg());
    }

    public static ResponseData ok() {
        return new ResponseData();
    }

}
