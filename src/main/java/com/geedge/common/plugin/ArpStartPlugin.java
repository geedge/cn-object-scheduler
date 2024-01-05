package com.geedge.common.plugin;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;

/**
 * Desc: TODO
 *
 * @return {@link null}
 * @created by wWei
 * @date 2024/1/3 18:44
 */
public class ArpStartPlugin extends ActiveRecordPlugin {

    private static final Log log = LogFactory.get();

    public ArpStartPlugin(IDataSourceProvider dataSourceProvider) {
        super(dataSourceProvider);
    }

    @Override
    public boolean start() {
        try {
            super.start();
        } catch (RuntimeException e) {
            log.error("ArpStartPlugin init error:{}", e.getMessage());
        }
        return true;
    }
}
