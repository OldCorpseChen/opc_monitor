package com.occ.opc_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OpcMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpcMonitorApplication.class, args);
    }

}
