package com.geedge.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.geedge.common.constant.TsgObject;
import com.geedge.common.util.TsgUtil;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @Classname Scheduler
 * @Date 2024/1/2 11:31
 * @Author wWei
 */
@Slf4j
@Component
public class FqdnScheduler {

    @Value("${tsg.object.fqdn.cyberghostvpn_servername.id}")
    private Integer cyberghostvpnServernameId;
    @Value("${tsg.object.fqdn.cyberghostvpn_servername.name}")
    private String cyberghostvpnServernameName;
    @Value("${tsg.object.fqdn.cyberghostvpn_servername.update.enable}")
    private Boolean cyberghostvpnServernameUpdateEnable;
    @Value("${tsg.object.fqdn.cyberghostvpn_servername.update.sql}")
    private String cyberghostvpnServernameSql;
    @Value("${tsg.object.fqdn.cyberghostvpn_servername.delete.enable}")
    private Boolean cyberghostvpnServernameDeleteEnable;
    @Value("${tsg.object.fqdn.cyberghostvpn_servername.delete.offsetSecond}")
    private Integer cyberghostvpnServernameOffsetSecond;
    public Counter cyberghostvpnServernameCounter = Counter.build("cyberghostvpn_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.cyberghostvpn_servername.update.cron}")
    public void updateCyberghostvpn() {
        executeUpdate(cyberghostvpnServernameId, cyberghostvpnServernameName, cyberghostvpnServernameUpdateEnable, cyberghostvpnServernameSql, cyberghostvpnServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.cyberghostvpn_servername.delete.cron}")
    public void deleteCyberghostvpn() {
        executeDelete(cyberghostvpnServernameId, cyberghostvpnServernameDeleteEnable, cyberghostvpnServernameOffsetSecond);
    }

    @Value("${tsg.object.fqdn.ipvanishvpn_servername.id}")
    private Integer ipvanishvpnServernameId;
    @Value("${tsg.object.fqdn.ipvanishvpn_servername.name}")
    private String ipvanishvpnServernameName;
    @Value("${tsg.object.fqdn.ipvanishvpn_servername.update.enable}")
    private Boolean ipvanishvpnServernameUpdateEnable;
    @Value("${tsg.object.fqdn.ipvanishvpn_servername.update.sql}")
    private String ipvanishvpnServernameSql;
    @Value("${tsg.object.fqdn.ipvanishvpn_servername.delete.enable}")
    private Boolean ipvanishvpnServernameDeleteEnable;
    @Value("${tsg.object.fqdn.ipvanishvpn_servername.delete.offsetSecond}")
    private Integer ipvanishvpnServernameOffsetSecond;
    public Counter ipvanishvpnServernameCounter = Counter.build("ipvanishvpn_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.ipvanishvpn_servername.update.cron}")
    public void updateIpvanishvpn() {
        executeUpdate(ipvanishvpnServernameId, ipvanishvpnServernameName, ipvanishvpnServernameUpdateEnable, ipvanishvpnServernameSql, ipvanishvpnServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.ipvanishvpn_servername.delete.cron}")
    public void deleteIpvanishvpn() {
        executeDelete(ipvanishvpnServernameId, ipvanishvpnServernameDeleteEnable, ipvanishvpnServernameOffsetSecond);
    }


    @Value("${tsg.object.fqdn.ivacyvpn_servername.id}")
    private Integer ivacyvpnServernameId;
    @Value("${tsg.object.fqdn.ivacyvpn_servername.name}")
    private String ivacyvpnServernameName;
    @Value("${tsg.object.fqdn.ivacyvpn_servername.update.enable}")
    private Boolean ivacyvpnServernameUpdateEnable;
    @Value("${tsg.object.fqdn.ivacyvpn_servername.update.sql}")
    private String ivacyvpnServernameSql;
    @Value("${tsg.object.fqdn.ivacyvpn_servername.delete.enable}")
    private Boolean ivacyvpnServernameDeleteEnable;
    @Value("${tsg.object.fqdn.ivacyvpn_servername.delete.offsetSecond}")
    private Integer ivacyvpnServernameOffsetSecond;
    public Counter ivacyvpnServernameCounter = Counter.build("ivacyvpn_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.ivacyvpn_servername.update.cron}")
    public void updateIvacyvpn() {
        executeUpdate(ivacyvpnServernameId, ivacyvpnServernameName, ivacyvpnServernameUpdateEnable, ivacyvpnServernameSql, ivacyvpnServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.ivacyvpn_servername.delete.cron}")
    public void deleteIvacyvpn() {
        executeDelete(ivacyvpnServernameId, ivacyvpnServernameDeleteEnable, ivacyvpnServernameOffsetSecond);
    }

    @Value("${tsg.object.fqdn.vpnunlimited_servername.id}")
    private Integer vpnunlimitedServernameId;
    @Value("${tsg.object.fqdn.vpnunlimited_servername.name}")
    private String vpnunlimitedServernameName;
    @Value("${tsg.object.fqdn.vpnunlimited_servername.update.enable}")
    private Boolean vpnunlimitedServernameUpdateEnable;
    @Value("${tsg.object.fqdn.vpnunlimited_servername.update.sql}")
    private String vpnunlimitedServernameSql;
    @Value("${tsg.object.fqdn.vpnunlimited_servername.delete.enable}")
    private Boolean vpnunlimitedServernameDeleteEnable;
    @Value("${tsg.object.fqdn.vpnunlimited_servername.delete.offsetSecond}")
    private Integer vpnunlimitedServernameOffsetSecond;
    public Counter vpnunlimitedServernameCounter = Counter.build("vpnunlimited_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.vpnunlimited_servername.update.cron}")
    public void updateVpnunlimited() {
        executeUpdate(vpnunlimitedServernameId, vpnunlimitedServernameName, vpnunlimitedServernameUpdateEnable, vpnunlimitedServernameSql, vpnunlimitedServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.vpnunlimited_servername.delete.cron}")
    public void deleteVpnunlimited() {
        executeDelete(vpnunlimitedServernameId, vpnunlimitedServernameDeleteEnable, vpnunlimitedServernameOffsetSecond);
    }

    @Value("${tsg.object.fqdn.vpn4fame_servername.id}")
    private Integer vpn4fameServernameId;
    @Value("${tsg.object.fqdn.vpn4fame_servername.name}")
    private String vpn4fameServernameName;
    @Value("${tsg.object.fqdn.vpn4fame_servername.update.enable}")
    private Boolean vpn4fameServernameUpdateEnable;
    @Value("${tsg.object.fqdn.vpn4fame_servername.update.sql}")
    private String vpn4fameServernameSql;
    @Value("${tsg.object.fqdn.vpn4fame_servername.delete.enable}")
    private Boolean vpn4fameServernameDeleteEnable;
    @Value("${tsg.object.fqdn.vpn4fame_servername.delete.offsetSecond}")
    private Integer vpn4fameServernameOffsetSecond;
    public Counter vpn4fameServernameCounter = Counter.build("vpn4fame_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.vpn4fame_servername.update.cron}")
    public void updateVpn4fame() {
        executeUpdate(vpn4fameServernameId, vpn4fameServernameName, vpn4fameServernameUpdateEnable, vpn4fameServernameSql, vpn4fameServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.vpn4fame_servername.delete.cron}")
    public void deleteVpn4fame() {
        executeDelete(vpn4fameServernameId, vpn4fameServernameDeleteEnable, vpn4fameServernameOffsetSecond);
    }

    @Value("${tsg.object.fqdn.windscribevpn_servername.id}")
    private Integer windscribevpnServernameId;
    @Value("${tsg.object.fqdn.windscribevpn_servername.name}")
    private String windscribevpnServernameName;
    @Value("${tsg.object.fqdn.windscribevpn_servername.update.enable}")
    private Boolean windscribevpnServernameUpdateEnable;
    @Value("${tsg.object.fqdn.windscribevpn_servername.update.sql}")
    private String windscribevpnServernameSql;
    @Value("${tsg.object.fqdn.windscribevpn_servername.delete.enable}")
    private Boolean windscribevpnServernameDeleteEnable;
    @Value("${tsg.object.fqdn.windscribevpn_servername.delete.offsetSecond}")
    private Integer windscribevpnServernameOffsetSecond;
    public Counter windscribevpnServernameCounter = Counter.build("windscribevpn_servername_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.fqdn.windscribevpn_servername.update.cron}")
    public void updateWindscribevpn() {
        executeUpdate(windscribevpnServernameId, windscribevpnServernameName, windscribevpnServernameUpdateEnable, windscribevpnServernameSql, windscribevpnServernameCounter);
    }

    @Scheduled(cron = "${tsg.object.fqdn.windscribevpn_servername.delete.cron}")
    public void deleteWindscribevpn() {
        executeDelete(windscribevpnServernameId, windscribevpnServernameDeleteEnable, windscribevpnServernameOffsetSecond);
    }

    private static void executeUpdate(Integer id, String name, Boolean enable, String sql, Counter counter) {
        if (TsgUtil.isLatestVersion) {
            executeUpdateLatest(id, name, enable, sql, counter);
        } else {
            executeUpdateOld(id, name, enable, sql, counter);
        }
    }

    private static void executeDelete(Integer id, Boolean enable, Integer offsetSecond) {
        if (TsgUtil.isLatestVersion) {
            executeDeleteLatest(id, enable, offsetSecond);
        } else {
            executeDeleteOld(id, enable, offsetSecond);
        }
    }

    private static void executeUpdateLatest(Integer id, String name, Boolean enable, String sql, Counter counter) {
        log.info("{}: started update task.", id);
        if (BooleanUtil.isFalse(enable)) {
            log.warn("{}: interrupted update task. enable: {}", id, enable);
            return;
        }
        try {
            Stopwatch watch = Stopwatch.createStarted();
            List<Record> data = Db.find(sql);
            log.info("{}: query knowledge base content, cost {} seconds", id, watch.elapsed(TimeUnit.SECONDS));
            watch.reset().start();
            List<Map<String, Object>> items = Lists.newArrayList();
            for (Record record : data) {
                Map<String, Object> item = Maps.newHashMap();
                String domain = record.get("domain");

                List<Map<String, Object>> patterns = Lists.newArrayList();
                Map<String, Object> pattern = Maps.newHashMap();
                pattern.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_PATTERNS_KEYWORDS, domain);
                patterns.add(pattern);

                Map<String, Object> str = Maps.newHashMap();
                str.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_PATTERNS, patterns);
                str.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_EXPR_TYPE, TsgObject.VALUE_OBJECT_MEMBER_ITEMS_STRING_EXPR_TYPE_0);
                str.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_IS_HEXBIN, TsgObject.VALUE_OBJECT_MEMBER_ITEMS_STRING_IS_HEXBIN_0);

                item.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_OP, TsgObject.VALUE_OBJECT_MEMBER_ITEMS_OP_ADD);
                item.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING, str);

                items.add(item);
            }

            Map<String, Object> member = Maps.newHashMap();
            member.put(TsgObject.KEY_OBJECT_MEMBER_TYPE, TsgObject.VALUE_OBJECT_MEMBER_TYPE_1);
            member.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS, items);

            Map<String, Object> obj = Maps.newHashMap();
            obj.put(TsgObject.KEY_OBJECT_NAME, name);
            obj.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_FQDN);
            obj.put(TsgObject.KEY_OBJECT_MEMBER, member);

            Map<String, Object> body = Maps.newHashMap();
            body.put(TsgObject.KEY_VSYS_ID, TsgObject.VALUE_VSYS_ID_1);
            body.put(TsgObject.KEY_OBJECT, obj);
            log.info("{}: build api params, items size: {}, cost {} seconds", id, items.size(), watch.elapsed(TimeUnit.SECONDS));
            if (items.isEmpty()) {
                return;
            }
            TsgUtil.updateObjectById(id, body);
            counter.inc(items.size());
        } catch (Exception e) {
            log.error("{}: failed to execute update task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static void executeUpdateOld(Integer id, String name, Boolean enable, String sql, Counter counter) {
        log.info("{}: started update task.", id);
        if (BooleanUtil.isFalse(enable)) {
            log.warn("{}: interrupted update task. enable: {}", id, enable);
            return;
        }
        try {
            Stopwatch watch = Stopwatch.createStarted();
            List<Record> data = Db.find(sql);
            log.info("{}: query knowledge base content, cost {} seconds", id, watch.elapsed(TimeUnit.SECONDS));
            watch.reset().start();

            List<Map<String, Object>> items = Lists.newArrayList();
            for (Record record : data) {
                String domain = record.get("domain");
                List<String> keywordArray = new ArrayList<>();
                keywordArray.add(domain);
                JSONObject obj = new JSONObject();
                obj.set("isHexbin", 0);
                obj.set(TsgObject.TSG_IS_BUILTIN_V2310, 0);
                obj.set(TsgObject.TSG_KEYWORDARRAY_V2310, keywordArray);
                items.add(obj);
            }

            JSONArray array = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.set(TsgObject.TSG_OBJECT_ID_V2310, id);
            jsonObject.set(TsgObject.TSG_OBJECT_TYPE_V2310, TsgObject.TSG_FQDN_OBJECT_V2310);
            jsonObject.set(TsgObject.TSG_OBJECT_NAME_V2310, name);
            jsonObject.set(TsgObject.TSG_IS_BUILTIN_V2310, 0);
            jsonObject.set(TsgObject.TSG_IS_EXCLUSION_V2310, 0);
            jsonObject.set(TsgObject.TSG_IS_VALID_V2310, 1);
            jsonObject.set("addItemList", items);
            log.info("{}: build api params, items size: {}, cost {} seconds", id, items.size(), watch.elapsed(TimeUnit.SECONDS));
            if (items.isEmpty()) {
                return;
            }
            array.add(jsonObject);
            JSONObject body = new JSONObject();
            body.set(TsgObject.TSG_OP_ACTION_V2310, TsgObject.TSG_UPDATE_V2310);
            body.set(TsgObject.TSG_OBJECT_LIST_V2310, array);
            TsgUtil.updateObjectOld(body);
            counter.inc(items.size());
        } catch (Exception e) {
            log.error("{}: failed to execute update task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static void executeDeleteLatest(Integer id, Boolean enable, Integer offsetSecond) {
        log.info("{}: started delete task.", id);
        if (BooleanUtil.isFalse(enable)) {
            log.warn("{}: interrupted delete task. enable: {}", id, enable);
            return;
        }
        try {
            DateTime deleteCursor = DateUtil.offsetSecond(new Date(), offsetSecond).setTimeZone(TimeZone.getTimeZone("UTC"));
            String deleteCursorStr = DateUtil.format(deleteCursor, DatePattern.UTC_PATTERN);

            JSONObject objectItemList = TsgUtil.getObjectItemList(id, "fqdn");
            Object data = objectItemList.get("data");
            if (StrUtil.isEmptyIfStr(data)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Map<String, Object> dataMap = (Map<String, Object>) data;
            Object itemsObj = dataMap.get("items");
            if (StrUtil.isEmptyIfStr(itemsObj)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsObj;
            if (itemList.isEmpty()) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Map<String, Object> map = itemList.get(0);
            if (StrUtil.isEmptyIfStr(map)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Object itemObj = map.get("string");
            if (StrUtil.isEmptyIfStr(itemObj)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Map<String, Object> itemMap = (Map<String, Object>) itemObj;
            Object createdTimeObj = itemMap.get("created_time");
            DateTime latestCreatedDateTime = DateUtil.parse(createdTimeObj.toString());
            int compare = DateUtil.compare(deleteCursor, latestCreatedDateTime);
            if (compare > 0) {
                log.warn("Does not need to be deleted, avoid item to empty");
                return;
            }

            Map<String, Object> form = Maps.newHashMap();
            form.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_FQDN);
            form.put(TsgObject.KEY_VSYS_ID, TsgObject.VALUE_VSYS_ID_1);
            form.put(TsgObject.KEY_CREATED_BEFORE, deleteCursorStr);
            TsgUtil.deleteItemOfObjectById(id, form);
        } catch (Exception e) {
            log.error("{}: failed to execute delete task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void executeDeleteOld(Integer id, Boolean enable, Integer offsetSecond) {
        log.info("{}: started delete task.", id);
        if (BooleanUtil.isFalse(enable)) {
            log.warn("{}: interrupted delete task. enable: {}", id, enable);
            return;
        }
        try {
            DateTime dateTime = DateUtil.offsetSecond(new Date(), offsetSecond).setTimeZone(TimeZone.getTimeZone("UTC"));
            String datetimeZ = DateUtil.format(dateTime, DatePattern.UTC_PATTERN);
            Map<String, Object> body = new HashMap<>(16);
            body.put(TsgObject.TSG_DELETE_ITEMS_BYL_TTIME_V2310, datetimeZ);
            body.put(TsgObject.TSG_ITEM_TYPE_V2310, TsgObject.TSG_FQDN_OBJECT_V2310);
            body.put(TsgObject.TSG_VSYS_ID_V2310, 1);
            List<Object> objectIds = new ArrayList<>();
            objectIds.add(id);
            body.put(TsgObject.TSG_TSG_OBJECT_IDS_V2310, objectIds);
            TsgUtil.deleteItemOfObjectOld(body);
        } catch (Exception e) {
            log.error("{}: failed to execute delete task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
