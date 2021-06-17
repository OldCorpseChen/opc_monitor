package com.occ.opc_monitor.config;

import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class opcConfig {

    @Value("${opcHost}")
    private String host;

    @Value("${opcDomain}")
    private String domain;

    @Value("${opcUser}")
    private String user;

    @Value("${opcPassword}")
    private String password;

    @Value("${opcClsid}")
    private String clsid;

    @Bean
    public Server opc_server(){
        // 连接信息
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost(host);         // 电脑IP
        ci.setDomain(domain);                  // 域，为空就行
        ci.setUser(user);             // 电脑上自己建好的用户名
        ci.setPassword(password);          // 密码

        // 使用KEPServer的配置
        ci.setClsid(clsid); // KEPServer的注册表ID，可以在“组件服务”里看到，上面有图片说明
//        ci.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729"); // KEPServer的注册表ID，可以在“组件服务”里看到，上面有图片说明
        //final String itemId = "qweq";    // KEPServer上配置的项的名字，没有实际PLC，用的模拟器：simulator
        // final String itemId = "通道 1.设备 1.标记 1";

        // 启动服务
        return new Server(ci, Executors.newSingleThreadScheduledExecutor());
    }
}
