package com.ecycle.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wangweichen
 */
@SpringBootApplication
@EnableFeignClients
public class GateWayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayServiceApplication.class, args);
    }

}
