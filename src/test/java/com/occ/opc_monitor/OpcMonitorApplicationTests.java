package com.occ.opc_monitor;

import com.occ.opc_monitor.service.opcService;
import org.jinterop.dcom.common.JIException;
import org.junit.jupiter.api.Test;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;

@SpringBootTest
class OpcMonitorApplicationTests {
    @Autowired
    private opcService opcServer;

    @Test
    void contextLoads() throws UnknownHostException, AlreadyConnectedException, JIException {
    }

}
