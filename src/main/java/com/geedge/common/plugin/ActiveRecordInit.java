package com.geedge.common.plugin;

import com.jfinal.plugin.druid.DruidPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Desc: TODO
 *
 * @return {@link null}
 * @created by wWei
 * @date 2024/1/3 18:44
 */
@Slf4j
@Component
public class ActiveRecordInit {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void initDialect() {
        DruidPlugin dp = new DruidPlugin(url, userName, password);
        dp.setTestWhileIdle(true);
        dp.setValidationQuery("SELECT 1");
        ArpStartPlugin arp = new ArpStartPlugin(dp);
        dp.start();
        arp.start();
        log.info("Initializing ActiveRecordPlugin Complete");
    }
}
