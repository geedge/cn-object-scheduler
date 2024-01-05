package com.geedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wangwei
 */
@SpringBootApplication
@EnableScheduling
public class CnPolicySchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnPolicySchedulerApplication.class, args);
    }

}
