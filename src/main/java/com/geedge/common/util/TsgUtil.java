package com.geedge.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @Classname LoginTsgUtil
 * @Date 2024/1/3 09:34
 * @Author wWei
 */
@Slf4j
@Component
public class TsgUtil {

    public static String TSG_URL;
    private static String TSG_USERNAME;
    private static String TSG_PASSWORD;
    private static Integer httpTimeOut;
    private static String TSG_TOKEN;

    @Value("${tsg.system.httpTimeout}")
    public void setTsgUrl(Integer timeOut) {
        httpTimeOut = timeOut;
    }

    @Value("${tsg.system.url}")
    public void setTsgUrl(String url) {
        TSG_URL = url;
    }

    @Value("${tsg.system.username}")
    private void setTsgUsername(String username) {
        TSG_USERNAME = username;
    }

    @Value("${tsg.system.password}")
    private void setTsgPassword(String password) {
        TSG_PASSWORD = password;
    }

    @Value("${tsg.system.token}")
    private void setTsgToken(String token) {
        TSG_TOKEN = token;
    }

    public static String getToken() {
        if (StrUtil.isNotEmpty(TSG_TOKEN)) {
            return TSG_TOKEN;
        }
        String token = "";
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", TSG_USERNAME);
        map.put("password", TSG_PASSWORD);
        try {
            Stopwatch watch = Stopwatch.createStarted();
            String response = HttpRequest.post(TSG_URL + "/v1/user/login")
                    .body(JSONUtil.toJsonStr(map))
                    .timeout(httpTimeOut)
                    .execute().body();
            log.info("get tsg-api token, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
            if (StrUtil.isBlank(response)) {
                log.error("failed to get TSG system token. response: {}", response);
                throw new RuntimeException("ailed to get TSG system token. response: " + response);
            }
            JSONObject jsonObject = JSONUtil.parseObj(response);
            if (!"200".equals(jsonObject.get("code").toString())) {
                log.error("failed to get TSG system token. response: {}", response);
                throw new RuntimeException("ailed to get TSG system token. response: " + response);
            }
            token = JSONUtil.parseObj(jsonObject.get("data")).get("token").toString();
        } catch (Exception e) {
            log.error("failed to get TSG system token. message: {}", e.getMessage());
            throw e;
        }
        return token;
    }

    public static void updateObjectById(Integer id, Map<String, Object> body) {
        Stopwatch watch = Stopwatch.createStarted();
        String response = HttpRequest.put(TSG_URL + "/v1/policy/object/" + id)
                .header(Header.AUTHORIZATION, getToken())
                .body(JSONUtil.toJsonStr(body))
                .timeout(httpTimeOut)
                .execute().body();
        log.info("update tsg-api, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
        if (StrUtil.isBlank(response)) {
            log.error("update {} Object error, response: {}", id, response);
            throw new RuntimeException("update " + id + " Object error, response: " + response);
        }
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (!"200".equals(jsonObject.get("code").toString())) {
            log.error("update {} Object error, response: {}", id, response);
            throw new RuntimeException("update " + id + " Object error, response: " + response);
        }
    }

    public static void deleteItemOfObjectById(Integer id, Map<String, Object> form) {
        Stopwatch watch = Stopwatch.createStarted();
        String response = HttpRequest.delete(TSG_URL + "/v1/policy/object/" + id + "/item")
                .header(Header.AUTHORIZATION, getToken())
                .form(form)
                .timeout(httpTimeOut)
                .execute().body();
        log.info("delete tsg-api, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
        if (StrUtil.isBlank(response)) {
            log.error("update {} Object error, response: {}", id, response);
            throw new RuntimeException("update " + id + " Object error, response: " + response);
        }
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (!"200".equals(jsonObject.get("code").toString())) {
            log.error("update {} Object error, response: {}", id, response);
            throw new RuntimeException("update " + id + " Object error, response: " + response);
        }
    }
}
