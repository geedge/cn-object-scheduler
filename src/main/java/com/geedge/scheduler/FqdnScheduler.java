package com.geedge.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import com.geedge.common.constant.TsgObject;
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
    public void updateCyberghostvpn() {
        executeUpdate(cyberghostvpnServernameId, cyberghostvpnServernameName, cyberghostvpnServernameUpdateEnable, cyberghostvpnServernameSql);
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

    @Scheduled(cron = "${tsg.object.fqdn.ipvanishvpn_servername.update.cron}")
    public void updateIpvanishvpn() {
        executeUpdate(ipvanishvpnServernameId, ipvanishvpnServernameName, ipvanishvpnServernameUpdateEnable, ipvanishvpnServernameSql);
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


    @Scheduled(cron = "${tsg.object.fqdn.ivacyvpn_servername.update.cron}")
    public void updateIvacyvpn() {
        executeUpdate(ivacyvpnServernameId, ivacyvpnServernameName, ivacyvpnServernameUpdateEnable, ivacyvpnServernameSql);
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


    @Scheduled(cron = "${tsg.object.fqdn.vpnunlimited_servername.update.cron}")
    public void updateVpnunlimited() {
        executeUpdate(vpnunlimitedServernameId, vpnunlimitedServernameName, vpnunlimitedServernameUpdateEnable, vpnunlimitedServernameSql);
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


    @Scheduled(cron = "${tsg.object.fqdn.vpn4fame_servername.update.cron}")
    public void updateVpn4fame() {
        executeUpdate(vpn4fameServernameId, vpn4fameServernameName, vpn4fameServernameUpdateEnable, vpn4fameServernameSql);
    }

    @Scheduled(cron = "${tsg.object.fqdn.vpn4fame_servername.delete.cron}")
    public void deleteVpn4fame() {
        executeDelete(vpn4fameServernameId, vpn4fameServernameDeleteEnable, vpn4fameServernameOffsetSecond);
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

                List<Map<String, Object>> patterns = Lists.newArrayList();
                Map<String, Object> pattern = Maps.newHashMap();
                pattern.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_STRING_PATTERNS_KEYWORDS, "$" + domain);
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
