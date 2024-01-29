package com.geedge.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.net.MaskBit;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.geedge.common.constant.TsgObject;
import com.geedge.common.enums.AddressFormat;
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

    @Value("${tsg.object.ip.excludeValue}")
    public void setExcludeList(String values) {
        if (StrUtil.isBlank(values)) {
            return;
        }
        excludeList = Arrays.asList(values.split(","));
    }

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
    public Counter cyberghostvpnServeripCounter = Counter.build("cyberghostvpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.cyberghostvpn_serverip.update.cron}")
    public void updateCyberghostvpn() {
        executeUpdate(cyberghostvpnServeripId, cyberghostvpnServeripName, cyberghostvpnServeripUpdateEnable, cyberghostvpnServeripSql, cyberghostvpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.cyberghostvpn_serverip.delete.cron}")
    public void deleteCyberghostvpn() {
        executeDelete(cyberghostvpnServeripId, cyberghostvpnServeripDeleteEnable, cyberghostvpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.hotspotvpn_serverip.id}")
    private Integer hotspotvpnServeripId;
    @Value("${tsg.object.ip.hotspotvpn_serverip.name}")
    private String hotspotvpnServeripName;
    @Value("${tsg.object.ip.hotspotvpn_serverip.update.enable}")
    private Boolean hotspotvpnServeripUpdateEnable;
    @Value("${tsg.object.ip.hotspotvpn_serverip.update.sql}")
    private String hotspotvpnServeripSql;
    @Value("${tsg.object.ip.hotspotvpn_serverip.delete.enable}")
    private Boolean hotspotvpnServeripDeleteEnable;
    @Value("${tsg.object.ip.hotspotvpn_serverip.delete.offsetSecond}")
    private Integer hotspotvpnServeripOffsetSecond;
    public Counter hotspotvpnServeripCounter = Counter.build("hotspotvpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.hotspotvpn_serverip.update.cron}")
    public void updateHotspotvpn() {
        executeUpdate(hotspotvpnServeripId, hotspotvpnServeripName, hotspotvpnServeripUpdateEnable, hotspotvpnServeripSql, hotspotvpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.hotspotvpn_serverip.delete.cron}")
    public void deleteHotspotvpn() {
        executeDelete(hotspotvpnServeripId, hotspotvpnServeripDeleteEnable, hotspotvpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.ipvanishvpn_serverip.id}")
    private Integer ipvanishvpnServeripId;
    @Value("${tsg.object.ip.ipvanishvpn_serverip.name}")
    private String ipvanishvpnServeripName;
    @Value("${tsg.object.ip.ipvanishvpn_serverip.update.enable}")
    private Boolean ipvanishvpnServeripUpdateEnable;
    @Value("${tsg.object.ip.ipvanishvpn_serverip.update.sql}")
    private String ipvanishvpnServeripSql;
    @Value("${tsg.object.ip.ipvanishvpn_serverip.delete.enable}")
    private Boolean ipvanishvpnServeripDeleteEnable;
    @Value("${tsg.object.ip.ipvanishvpn_serverip.delete.offsetSecond}")
    private Integer ipvanishvpnServeripOffsetSecond;
    public Counter ipvanishvpnServeripCounter = Counter.build("ipvanishvpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.ipvanishvpn_serverip.update.cron}")
    public void updateIpvanishvpn() {
        executeUpdate(ipvanishvpnServeripId, ipvanishvpnServeripName, ipvanishvpnServeripUpdateEnable, ipvanishvpnServeripSql, ipvanishvpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.ipvanishvpn_serverip.delete.cron}")
    public void deleteIpvanishvpn() {
        executeDelete(ipvanishvpnServeripId, ipvanishvpnServeripDeleteEnable, ipvanishvpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.geckovpn_serverip.id}")
    private Integer geckovpnServeripId;
    @Value("${tsg.object.ip.geckovpn_serverip.name}")
    private String geckovpnServeripName;
    @Value("${tsg.object.ip.geckovpn_serverip.update.enable}")
    private Boolean geckovpnServeripUpdateEnable;
    @Value("${tsg.object.ip.geckovpn_serverip.update.sql}")
    private String geckovpnServeripSql;
    @Value("${tsg.object.ip.geckovpn_serverip.delete.enable}")
    private Boolean geckovpnServeripDeleteEnable;
    @Value("${tsg.object.ip.geckovpn_serverip.delete.offsetSecond}")
    private Integer geckovpnServeripOffsetSecond;
    public Counter geckovpnServeripCounter = Counter.build("geckovpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.geckovpn_serverip.update.cron}")
    public void updateGeckovpn() {
        executeUpdate(geckovpnServeripId, geckovpnServeripName, geckovpnServeripUpdateEnable, geckovpnServeripSql, geckovpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.geckovpn_serverip.delete.cron}")
    public void deleteGeckovpn() {
        executeDelete(geckovpnServeripId, geckovpnServeripDeleteEnable, geckovpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.ivacyvpn_serverip.id}")
    private Integer ivacyvpnServeripId;
    @Value("${tsg.object.ip.ivacyvpn_serverip.name}")
    private String ivacyvpnServeripName;
    @Value("${tsg.object.ip.ivacyvpn_serverip.update.enable}")
    private Boolean ivacyvpnServeripUpdateEnable;
    @Value("${tsg.object.ip.ivacyvpn_serverip.update.sql}")
    private String ivacyvpnServeripSql;
    @Value("${tsg.object.ip.ivacyvpn_serverip.delete.enable}")
    private Boolean ivacyvpnServeripDeleteEnable;
    @Value("${tsg.object.ip.ivacyvpn_serverip.delete.offsetSecond}")
    private Integer ivacyvpnServeripOffsetSecond;
    public Counter ivacyvpnServeripCounter = Counter.build("ivacyvpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.ivacyvpn_serverip.update.cron}")
    public void updateIvacyvpn() {
        executeUpdate(ivacyvpnServeripId, ivacyvpnServeripName, ivacyvpnServeripUpdateEnable, ivacyvpnServeripSql, ivacyvpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.ivacyvpn_serverip.delete.cron}")
    public void deleteIvacyvpn() {
        executeDelete(ivacyvpnServeripId, ivacyvpnServeripDeleteEnable, ivacyvpnServeripOffsetSecond);
    }


    @Value("${tsg.object.ip.turbovpn_serverip.id}")
    private Integer turbovpnServeripId;
    @Value("${tsg.object.ip.turbovpn_serverip.name}")
    private String turbovpnServeripName;
    @Value("${tsg.object.ip.turbovpn_serverip.update.enable}")
    private Boolean turbovpnServeripUpdateEnable;
    @Value("${tsg.object.ip.turbovpn_serverip.update.sql}")
    private String turbovpnServeripSql;
    @Value("${tsg.object.ip.turbovpn_serverip.delete.enable}")
    private Boolean turbovpnServeripDeleteEnable;
    @Value("${tsg.object.ip.turbovpn_serverip.delete.offsetSecond}")
    private Integer turbovpnServeripOffsetSecond;
    public Counter turbovpnServeripCounter = Counter.build("turbovpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.turbovpn_serverip.update.cron}")
    public void updateTurbovpn() {
        executeUpdate(turbovpnServeripId, turbovpnServeripName, turbovpnServeripUpdateEnable, turbovpnServeripSql, turbovpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.turbovpn_serverip.delete.cron}")
    public void deleteTurbovpn() {
        executeDelete(turbovpnServeripId, turbovpnServeripDeleteEnable, turbovpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.vpnunlimited_serverip.id}")
    private Integer vpnunlimitedServeripId;
    @Value("${tsg.object.ip.vpnunlimited_serverip.name}")
    private String vpnunlimitedServeripName;
    @Value("${tsg.object.ip.vpnunlimited_serverip.update.enable}")
    private Boolean vpnunlimitedServeripUpdateEnable;
    @Value("${tsg.object.ip.vpnunlimited_serverip.update.sql}")
    private String vpnunlimitedServeripSql;
    @Value("${tsg.object.ip.vpnunlimited_serverip.delete.enable}")
    private Boolean vpnunlimitedServeripDeleteEnable;
    @Value("${tsg.object.ip.vpnunlimited_serverip.delete.offsetSecond}")
    private Integer vpnunlimitedServeripOffsetSecond;
    public Counter vpnunlimitedServeripCounter = Counter.build("vpnunlimited_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.vpnunlimited_serverip.update.cron}")
    public void updateVpnunlimited() {
        executeUpdate(vpnunlimitedServeripId, vpnunlimitedServeripName, vpnunlimitedServeripUpdateEnable, vpnunlimitedServeripSql, vpnunlimitedServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.vpnunlimited_serverip.delete.cron}")
    public void deleteVpnunlimited() {
        executeDelete(vpnunlimitedServeripId, vpnunlimitedServeripDeleteEnable, vpnunlimitedServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.windscribevpn_serverip.id}")
    private Integer windscribevpnServeripId;
    @Value("${tsg.object.ip.windscribevpn_serverip.name}")
    private String windscribevpnServeripName;
    @Value("${tsg.object.ip.windscribevpn_serverip.update.enable}")
    private Boolean windscribevpnServeripUpdateEnable;
    @Value("${tsg.object.ip.windscribevpn_serverip.update.sql}")
    private String windscribevpnServeripSql;
    @Value("${tsg.object.ip.windscribevpn_serverip.delete.enable}")
    private Boolean windscribevpnServeripDeleteEnable;
    @Value("${tsg.object.ip.windscribevpn_serverip.delete.offsetSecond}")
    private Integer windscribevpnServeripOffsetSecond;
    public Counter windscribevpnServeripCounter = Counter.build("windscribevpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.windscribevpn_serverip.update.cron}")
    public void updateWindscribevpn() {
        executeUpdate(windscribevpnServeripId, windscribevpnServeripName, windscribevpnServeripUpdateEnable, windscribevpnServeripSql, windscribevpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.windscribevpn_serverip.delete.cron}")
    public void deleteWindscribevpn() {
        executeDelete(windscribevpnServeripId, windscribevpnServeripDeleteEnable, windscribevpnServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.vpn4fame_serverip.id}")
    private Integer vpn4fameServeripId;
    @Value("${tsg.object.ip.vpn4fame_serverip.name}")
    private String vpn4fameServeripName;
    @Value("${tsg.object.ip.vpn4fame_serverip.update.enable}")
    private Boolean vpn4fameServeripUpdateEnable;
    @Value("${tsg.object.ip.vpn4fame_serverip.update.sql}")
    private String vpn4fameServeripSql;
    @Value("${tsg.object.ip.vpn4fame_serverip.delete.enable}")
    private Boolean vpn4fameServeripDeleteEnable;
    @Value("${tsg.object.ip.vpn4fame_serverip.delete.offsetSecond}")
    private Integer vpn4fameServeripOffsetSecond;
    public Counter vpn4fameServeripCounter = Counter.build("vpn4fame_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.vpn4fame_serverip.update.cron}")
    public void updateVpn4fame() {
        executeUpdate(vpn4fameServeripId, vpn4fameServeripName, vpn4fameServeripUpdateEnable, vpn4fameServeripSql, vpn4fameServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.vpn4fame_serverip.delete.cron}")
    public void deleteVpn4fame() {
        executeDelete(vpn4fameServeripId, vpn4fameServeripDeleteEnable, vpn4fameServeripOffsetSecond);
    }

    @Value("${tsg.object.ip.protonvpn_ip.id}")
    private Integer protonvpnIpId;
    @Value("${tsg.object.ip.protonvpn_ip.name}")
    private String protonvpnIpName;
    @Value("${tsg.object.ip.protonvpn_ip.update.enable}")
    private Boolean protonvpnIpUpdateEnable;
    @Value("${tsg.object.ip.protonvpn_ip.update.sql}")
    private String protonvpnIpSql;
    @Value("${tsg.object.ip.protonvpn_ip.delete.enable}")
    private Boolean protonvpnIpDeleteEnable;
    @Value("${tsg.object.ip.protonvpn_ip.delete.offsetSecond}")
    private Integer protonvpnIpOffsetSecond;
    public Counter protonvpnIpCounter = Counter.build("protonvpn_ip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.protonvpn_ip.update.cron}")
    public void updateProtonvpn() {
        executeUpdate(protonvpnIpId, protonvpnIpName, protonvpnIpUpdateEnable, protonvpnIpSql, protonvpnIpCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.protonvpn_ip.delete.cron}")
    public void deleteProtonvpn() {
        executeDelete(protonvpnIpId, protonvpnIpDeleteEnable, protonvpnIpOffsetSecond);
    }

    @Value("${tsg.object.ip.expressvpn_ip.id}")
    private Integer expressvpnIpId;
    @Value("${tsg.object.ip.expressvpn_ip.name}")
    private String expressvpnIpName;
    @Value("${tsg.object.ip.expressvpn_ip.update.enable}")
    private Boolean expressvpnIpUpdateEnable;
    @Value("${tsg.object.ip.expressvpn_ip.update.sql}")
    private String expressvpnIpSql;
    @Value("${tsg.object.ip.expressvpn_ip.delete.enable}")
    private Boolean expressvpnIpDeleteEnable;
    @Value("${tsg.object.ip.expressvpn_ip.delete.offsetSecond}")
    private Integer expressvpnIpOffsetSecond;
    public Counter expressvpnIpCounter = Counter.build("expressvpn_ip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.expressvpn_ip.update.cron}")
    public void updateExpressvpn() {
        executeUpdate(expressvpnIpId, expressvpnIpName, expressvpnIpUpdateEnable, expressvpnIpSql, expressvpnIpCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.expressvpn_ip.delete.cron}")
    public void deleteExpressvpn() {
        executeDelete(expressvpnIpId, expressvpnIpDeleteEnable, expressvpnIpOffsetSecond);
    }

    @Value("${tsg.object.ip.psiphon3vpn_serverip.id}")
    private Integer psiphon3vpnServeripId;
    @Value("${tsg.object.ip.psiphon3vpn_serverip.name}")
    private String psiphon3vpnServeripName;
    @Value("${tsg.object.ip.psiphon3vpn_serverip.update.enable}")
    private Boolean psiphon3vpnServeripUpdateEnable;
    @Value("${tsg.object.ip.psiphon3vpn_serverip.update.sql}")
    private String psiphon3vpnServeripSql;
    @Value("${tsg.object.ip.psiphon3vpn_serverip.delete.enable}")
    private Boolean psiphon3vpnServeripDeleteEnable;
    @Value("${tsg.object.ip.psiphon3vpn_serverip.delete.offsetSecond}")
    private Integer psiphon3vpnServeripOffsetSecond;
    public Counter psiphon3vpnServeripCounter = Counter.build("psiphon3vpn_serverip_counter", "COUNTER HELP").register(CollectorRegistry.defaultRegistry);

    @Scheduled(cron = "${tsg.object.ip.psiphon3vpn_serverip.update.cron}")
    public void updatePsiphon3vpn() {
        executeUpdate(psiphon3vpnServeripId, psiphon3vpnServeripName, psiphon3vpnServeripUpdateEnable, psiphon3vpnServeripSql, psiphon3vpnServeripCounter);
    }

    @Scheduled(cron = "${tsg.object.ip.psiphon3vpn_serverip.delete.cron}")
    public void deletePsiphon3vpn() {
        executeDelete(psiphon3vpnServeripId, psiphon3vpnServeripDeleteEnable, psiphon3vpnServeripOffsetSecond);
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
            row:
            for (Record record : data) {
                Map<String, Object> item = Maps.newHashMap();
                String addressFormat = record.get("addrFormat");
                String ip1 = record.get("ip1");
                for (String excludeItem : excludeList) {
                    if (excludeItem.equals(ip1)) {
                        continue row;
                    }
                    if (excludeItem.endsWith("*") && ip1.startsWith(excludeItem.replace("*", ""))) {
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
                } else {
                    continue;
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
            row:
            for (Record record : data) {
                String addressFormat = record.get("addrFormat");
                String ip1 = record.get("ip1");
                if (StrUtil.isBlank(ip1)) {
                    continue;
                }
                for (String excludeItem : excludeList) {
                    if (excludeItem.equals(ip1)) {
                        continue row;
                    }
                    if (excludeItem.endsWith("*") && ip1.startsWith(excludeItem.replace("*", ""))) {
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
                    ip.put(TsgObject.TSG_ITEM_IP_V2310, ipMask);
                } else if (AddressFormat.RANGE.getValue().equalsIgnoreCase(addressFormat)) {
                    ip.put(TsgObject.TSG_ITEM_IP_V2310, ip1 + "-" + ip2);
                } else if (AddressFormat.SINGLE.getValue().equalsIgnoreCase(addressFormat)) {
                    ip.put(TsgObject.TSG_ITEM_IP_V2310, ip1);
                } else {
                    log.warn("address format parse error: {}", record);
                    continue;
                }
                ip.put(TsgObject.TSG_ITEM_PORT_V2310, TsgObject.TSG_VALUE_OBJECT_MEMBER_ITEMS_IP_PORT_RANGE_V2310);
                ip.put(TsgObject.TSG_ITEM_ISSESSION_V2310, TsgObject.TSG_ITEM_ENDPOINT_V2310);
                items.add(ip);
            }
            JSONArray array = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.set(TsgObject.TSG_OBJECT_ID_V2310, id);
            jsonObject.set(TsgObject.TSG_OBJECT_TYPE_V2310, TsgObject.TSG_IP_ADDR_OBJECT_V2310);
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

            JSONObject objectItemList = TsgUtil.getObjectItemList(id, "ip");
            Object data = objectItemList.get("data");
            if (StrUtil.isEmptyIfStr(data)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Map<String, Object> dataMap = (Map<String, Object>) data;
            Object itemsObj = dataMap.get("items");
            if (StrUtil.isEmptyIfStr(itemsObj) || StrUtil.isBlankIfStr(itemsObj)) {
                log.warn("No item does not need to be deleted");
                return;
            }
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsObj;
            if (itemList.isEmpty()) {
                log.warn("No item does not need to be deleted");
                return;
            }
            Map<String, Object> map = itemList.get(0);
            Object itemObj = map.get("ip");
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
            form.put(TsgObject.KEY_OBJECT_TYPE, TsgObject.VALUE_OBJECT_TYPE_IP);
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
            body.put(TsgObject.TSG_ITEM_TYPE_V2310, TsgObject.TSG_ITEM_IP_V2310);
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
