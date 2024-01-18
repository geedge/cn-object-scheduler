package com.geedge.common.monitor;

import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *
 * @Classname PrometheusMetrics
 * @Date 2024/1/9 10:22
 * @Author wWei
 */
@RestController
public class PrometheusMetrics extends MetricsServlet {

    @GetMapping("/metrics")
    public void redisMetrics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}

