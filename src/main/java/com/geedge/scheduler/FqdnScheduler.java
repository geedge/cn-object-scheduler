package com.geedge.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.net.MaskBit;
import cn.hutool.core.util.BooleanUtil;
import com.geedge.common.constant.TsgObject;
import com.geedge.common.enums.AddressFormat;
import com.geedge.common.util.TsgUtil;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
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


    @Scheduled(cron = "${tsg.object.fqdn.cyberghostvpn_servername.update.cron}")
    public void scheduledExecutorUpdate() {
        executeUpdate(cyberghostvpnServernameId, cyberghostvpnServernameName, cyberghostvpnServernameUpdateEnable, cyberghostvpnServernameSql);
    }

    @Scheduled(cron = "${tsg.object.fqdn.cyberghostvpn_servername.delete.cron}")
    public void scheduledExecutorDelete() {
        executeDelete(cyberghostvpnServernameId, cyberghostvpnServernameDeleteEnable, cyberghostvpnServernameOffsetSecond);
    }

    private static void executeUpdate(Integer id, String name, Boolean enable, String sql) {
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
                Map<String, Object> pattern = Maps.newHashMap();
                pattern.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_PATTERNS_KEYWORDS, domain);
                List<Map<String, Object>> patterns = Lists.newArrayList();
                patterns.add(pattern);
                Map<String, Object> str = Maps.newHashMap();
                str.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_PATTERNS, patterns);
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
            TsgUtil.updateObjectById(id, body);
        } catch (Exception e) {
            log.error("{}: failed to execute update task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void executeDelete(Integer id, Boolean enable, Integer offsetSecond) {
        log.info("{}: started delete task.", id);
        if (BooleanUtil.isFalse(enable)) {
            log.warn("{}: interrupted delete task. enable: {}", id, enable);
            return;
        }
        try {
            DateTime dateTime = DateUtil.offsetSecond(new Date(), offsetSecond).setTimeZone(TimeZone.getTimeZone("UTC"));
            String datetimeZ = DateUtil.format(dateTime, DatePattern.UTC_PATTERN);
            Map<String, Object> form = Maps.newHashMap();
            form.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_FQDN);
            form.put(TsgObject.KEY_VSYS_ID, TsgObject.VALUE_VSYS_ID_1);
            form.put(TsgObject.KEY_CREATED_BEFORE, datetimeZ);
            TsgUtil.deleteItemOfObjectById(id, form);
        } catch (Exception e) {
            log.error("{}: failed to execute delete task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
