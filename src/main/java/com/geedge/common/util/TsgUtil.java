package com.geedge.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Stopwatch;
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
    private static String TSG_TOKEN;
    private static Integer httpTimeOut;
    public static Boolean isLatestVersion;

    @Value("${tsg.system.isLatestVersion}")
    public void isLatestVersion(Boolean latestVersion) {
        isLatestVersion = latestVersion;
    }

    @Value("${tsg.system.httpTimeout}")
    public void setTsgUrl(Integer timeOut) {
        httpTimeOut = timeOut;
    }

    @Value("${tsg.system.url}")
    public void setTsgUrl(String url) {
        TSG_URL = url;
    }

    @Value("${tsg.system.token}")
    private void setTsgToken(String token) {
        TSG_TOKEN = token;
    }

    public static String getToken() {
        if (StrUtil.isNotEmpty(TSG_TOKEN)) {
            return TSG_TOKEN;
        }
        log.error("failed to get TSG system token.");
        throw new IllegalArgumentException("failed to get TSG system token.");
    }

    public static JSONObject getObjectItemList(Integer objectId, String objectType) {
        Stopwatch watch = Stopwatch.createStarted();
        String response = HttpRequest.get(TSG_URL + "/v1/policy/object/" + objectId + "/item?page_no=1&page_size=1&type=" + objectType)
                .header(Header.AUTHORIZATION, getToken())
                .timeout(httpTimeOut)
                .execute().body();
        log.info("get tsg-api, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
        if (StrUtil.isBlank(response)) {
            log.error("get {} Object error, response: {}", objectId, response);
            throw new RuntimeException("get " + objectId + " Object error, response: " + response);
        }
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (!"200".equals(jsonObject.get("code").toString())) {
            log.error("get {} Object error, response: {}", objectId, response);
            throw new RuntimeException("get " + objectId + " Object error, response: " + response);
        }
        return jsonObject;
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

    public static void updateObjectOld(Map<String, Object> body) {
        Stopwatch watch = Stopwatch.createStarted();
        String response = HttpRequest.put(TSG_URL + "/v1/policy/object")
                .header(Header.AUTHORIZATION, getToken())
                .body(JSONUtil.toJsonStr(body))
                .timeout(httpTimeOut)
                .execute().body();
        log.info("update tsg-api, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
        if (StrUtil.isBlank(response)) {
            log.error("update {} Object error, response: {}", body, response);
            throw new RuntimeException("update " + body + " Object error, response: " + response);
        }
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (!"200".equals(jsonObject.get("code").toString())) {
            log.error("update {} Object error, response: {}", body, response);
            throw new RuntimeException("update " + body + " Object error, response: " + response);
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

    public static void deleteItemOfObjectOld(Map<String, Object> body) {
        Stopwatch watch = Stopwatch.createStarted();
        String response = HttpRequest.delete(TSG_URL + "/v1/policy/items")
                .header(Header.AUTHORIZATION, getToken())
                .body(JSONUtil.toJsonStr(body))
                .timeout(httpTimeOut)
                .execute().body();
        log.info("delete tsg-api, cost {} seconds", watch.elapsed(TimeUnit.SECONDS));
        if (StrUtil.isBlank(response)) {
            log.error("update {} Object error, response: {}", body, response);
            throw new RuntimeException("update " + body + " Object error, response: " + response);
        }
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (!"200".equals(jsonObject.get("code").toString())) {
            log.error("update {} Object error, response: {}", body, response);
            throw new RuntimeException("update " + body + " Object error, response: " + response);
        }
    }
}
