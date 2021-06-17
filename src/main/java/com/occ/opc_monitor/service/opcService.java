package com.occ.opc_monitor.service;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;

import java.net.UnknownHostException;

public interface opcService {
    String getGroups() throws UnknownHostException, AlreadyConnectedException, JIException;
    String getItems(String groupName) throws UnknownHostException, AlreadyConnectedException, JIException;
    String saveBlock(String blockName,String outArg,String inArgs);
    void startMonitor(String blockName);
    String getBlockNames();
    String getOnlineMonitorList();
    void stopMonitor(String blockName);
    void startMonitorAllList();
    void stopMonitorAllList();
}
