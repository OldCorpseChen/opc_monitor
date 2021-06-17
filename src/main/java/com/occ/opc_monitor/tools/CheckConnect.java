package com.occ.opc_monitor.tools;

import com.occ.opc_monitor.config.opcConfig;
import org.openscada.opc.lib.da.AutoReconnectController;

public class CheckConnect extends Thread{
    private com.occ.opc_monitor.config.opcConfig opcConfig;
    public CheckConnect(opcConfig opcConfig){
        this.opcConfig=opcConfig;
    }

    @Override
    public void run() {
        opcConfig.opc_server();
        AutoReconnectController autoReconnectController=new AutoReconnectController(opcConfig.opc_server());
        autoReconnectController.connect();

    }
}
