package com.occ.opc_monitor.tools;

import com.occ.opc_monitor.config.opcConfig;
import lombok.SneakyThrows;
import org.openscada.opc.lib.da.AutoReconnectController;

public class ConnectThread extends Thread {
    private opcConfig opcConfig;
    public ConnectThread(opcConfig opcConfig){
        this.opcConfig=opcConfig;
    }
    private boolean alreadyWait=false;
    private boolean alreadyRun=false;

    @SneakyThrows
    @Override
    public void run() {
//        while (true){
//            try {
//                opcConfig.opc_server().connect();
//                break;
//            }catch (Exception e){
//                Thread.sleep(1000);
//            }
//        }
        AutoReconnectController autoReconnectController=new AutoReconnectController(opcConfig.opc_server(), 5000);
        autoReconnectController.connect();
//        while (true){
//            if (autoReconnectController.isRequested()&&!alreadyWait){
//                HashMap< String,AccessThread> threads=springConfig.threadHashMap;
//                for(Map.Entry<String,AccessThread> t:threads.entrySet()){
//                    t.getValue().wait();
//                }
//                alreadyWait=true;
//                alreadyRun=false;
//            }
//            if(!autoReconnectController.isRequested()&&!alreadyRun){
//                HashMap< String,AccessThread> threads=springConfig.threadHashMap;
//                for(Map.Entry<String,AccessThread> t:threads.entrySet()){
//                    t.getValue().notify();
//                }
//                alreadyRun=true;
//                alreadyWait=false;
//            }
//        }
    }
}
