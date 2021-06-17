package com.occ.opc_monitor.config;

import com.occ.opc_monitor.tools.AccessThread;
import com.occ.opc_monitor.tools.ConnectThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashMap;

@Configuration
@ComponentScan("com.occ.opc_monitor.service")
@Import(MybatisConfig.class)
public class springConfig implements ApplicationRunner {
    @Autowired
    private opcConfig opcConfig;

    public static HashMap<String, AccessThread> threadHashMap=new HashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        opcConfig.opc_server().connect();
        new ConnectThread(opcConfig).start();
    }
}
