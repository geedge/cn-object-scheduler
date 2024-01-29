package com.geedge.controller;

import com.geedge.common.ResponseData;
import com.geedge.scheduler.FqdnScheduler;
import com.geedge.scheduler.IpScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Classname ArtificialTriggerController
 * @Date 2024/1/29 14:08
 * @Author wWei
 */
@RestController("/")
public class ArtificialTriggerController {
    private FqdnScheduler fqdnScheduler;
    private IpScheduler ipScheduler;

    @PostMapping("trigger")
    public ResponseData triggerAll() {
        fqdnScheduler.updateCyberghostvpn();
        fqdnScheduler.deleteCyberghostvpn();

        fqdnScheduler.updateIvacyvpn();
        fqdnScheduler.deleteIvacyvpn();

        fqdnScheduler.updateIpvanishvpn();
        fqdnScheduler.deleteIpvanishvpn();

        fqdnScheduler.updateVpnunlimited();
        fqdnScheduler.deleteVpnunlimited();

        fqdnScheduler.updateWindscribevpn();
        fqdnScheduler.deleteWindscribevpn();

        fqdnScheduler.updateVpn4fame();
        fqdnScheduler.deleteVpn4fame();

        ipScheduler.updateCyberghostvpn();
        ipScheduler.deleteCyberghostvpn();

        ipScheduler.updateHotspotvpn();
        ipScheduler.deleteHotspotvpn();

        ipScheduler.updateIpvanishvpn();
        ipScheduler.deleteIpvanishvpn();

        ipScheduler.updateGeckovpn();
        ipScheduler.deleteGeckovpn();

        ipScheduler.updateIvacyvpn();
        ipScheduler.deleteIvacyvpn();

        ipScheduler.updateTurbovpn();
        ipScheduler.deleteTurbovpn();

        ipScheduler.updateVpnunlimited();
        ipScheduler.deleteVpnunlimited();

        ipScheduler.updateWindscribevpn();
        ipScheduler.deleteWindscribevpn();

        ipScheduler.updateVpn4fame();
        ipScheduler.deleteVpn4fame();

        ipScheduler.updateProtonvpn();
        ipScheduler.deleteProtonvpn();

        ipScheduler.updateExpressvpn();
        ipScheduler.deleteExpressvpn();

        ipScheduler.updatePsiphon3vpn();
        ipScheduler.deletePsiphon3vpn();
        return ResponseData.ok();
    }

    @Autowired
    private void setFqdnScheduler(FqdnScheduler fqdnScheduler) {
        this.fqdnScheduler = fqdnScheduler;
    }

    @Autowired
    private void setIpScheduler(IpScheduler ipScheduler) {
        this.ipScheduler = ipScheduler;
    }
}
