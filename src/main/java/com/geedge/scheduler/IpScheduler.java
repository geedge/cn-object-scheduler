package com.geedge.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.net.MaskBit;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
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
public class IpScheduler {
    public static List<String> excludeList = new ArrayList<>();
    @Value("${tsg.object.ip.cyberghostvpn_serverip.id}")
    private Integer cyberghostvpnServeripId;
    @Value("${tsg.object.ip.cyberghostvpn_serverip.name}")
    private String cyberghostvpnServeripName;
    @Value("${tsg.object.ip.cyberghostvpn_serverip.update.enable}")
    private Boolean cyberghostvpnServeripUpdateEnable;
    @Value("${tsg.object.ip.cyberghostvpn_serverip.update.sql}")
    private String cyberghostvpnServeripSql;
    @Value("${tsg.object.ip.cyberghostvpn_serverip.delete.enable}")
    private Boolean cyberghostvpnServeripDeleteEnable;
    @Value("${tsg.object.ip.cyberghostvpn_serverip.delete.offsetSecond}")
    private Integer cyberghostvpnServeripOffsetSecond;

    @Value("${tsg.object.ip.excludeValue}")
    public void setExcludeList(String values) {
        if (StrUtil.isBlank(values)) {
            return;
        }
        excludeList = Arrays.asList(values.split(","));
    }

    @Scheduled(cron = "${tsg.object.ip.cyberghostvpn_serverip.update.cron}")
    public void scheduledExecutorUpdate() {
        executeUpdate(cyberghostvpnServeripId, cyberghostvpnServeripName, cyberghostvpnServeripUpdateEnable, cyberghostvpnServeripSql);
    }

    @Scheduled(cron = "${tsg.object.ip.cyberghostvpn_serverip.delete.cron}")
    public void scheduledExecutorDelete() {
        executeDelete(cyberghostvpnServeripId, cyberghostvpnServeripDeleteEnable, cyberghostvpnServeripOffsetSecond);
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
            row:
            for (Record record : data) {
                Map<String, Object> item = Maps.newHashMap();
                String addressFormat = record.get("addrFormat");
                String ip1 = record.get("ip1");
                for (String s : excludeList) {
                    if (s.equals(ip1)) {
                        continue row;
                    }
                    if (s.endsWith("*") && ip1.startsWith(s.replace("*", ""))) {
                        continue row;
                    }
                }
                String ip2 = record.get("ip2");
                Map<String, Object> ip = Maps.newHashMap();
                if (AddressFormat.CIDR.getValue().equalsIgnoreCase(addressFormat)) {
                    int maskBit = Integer.parseInt(ip2);
                    String mask = MaskBit.get(Integer.parseInt(ip2));
                    String beginIpStr = Ipv4Util.getBeginIpStr(ip1, maskBit);
                    String ipMask = Ipv4Util.formatIpBlock(beginIpStr, mask);
                    ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_IP_CIDR, ipMask);
                } else if (AddressFormat.RANGE.getValue().equalsIgnoreCase(addressFormat)) {
                    ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_IP_RANGE, ip1 + "-" + ip2);
                } else {
                    ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_IP_ADDRESS, ip1);
                }
                ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_PORT_RANGE, TsgObject.VALUE_OBJECT_MEMBER_ITEMS_IP_PORT_RANGE);
                InetAddress inetAddress = InetAddress.getByName(ip1);
                if (inetAddress instanceof Inet4Address) {
                    ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_ADDR_TYPE, 4);
                } else if (inetAddress instanceof Inet6Address) {
                    ip.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP_ADDR_TYPE, 6);
                }
                item.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_OP, TsgObject.VALUE_OBJECT_MEMBER_ITEMS_OP_ADD);
                item.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS_IP, ip);
                items.add(item);
            }
            Map<String, Object> member = Maps.newHashMap();
            member.put(TsgObject.KEY_OBJECT_MEMBER_TYPE, TsgObject.VALUE_OBJECT_MEMBER_TYPE_1);
            member.put(TsgObject.KEY_OBJECT_MEMBER_ITEMS, items);
            Map<String, Object> obj = Maps.newHashMap();
            obj.put(TsgObject.KEY_OBJECT_NAME, name);
            obj.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_IP);
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
            form.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_IP);
            form.put(TsgObject.KEY_VSYS_ID, TsgObject.VALUE_VSYS_ID_1);
            form.put(TsgObject.KEY_CREATED_BEFORE, datetimeZ);
            TsgUtil.deleteItemOfObjectById(id, form);
        } catch (Exception e) {
            log.error("{}: failed to execute delete task. message: {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
