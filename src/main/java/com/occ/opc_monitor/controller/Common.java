package com.occ.opc_monitor.controller;

import com.occ.opc_monitor.service.opcService;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequestMapping("Common")
public class Common {
    @Autowired
    private opcService opcService;


    @RequestMapping("getGroups")
    public String getGroups() throws UnknownHostException, AlreadyConnectedException, JIException {
        return opcService.getGroups();
    }

    @RequestMapping("getItems")
    public String getItems(@RequestParam("groupName")String groupName) throws UnknownHostException, AlreadyConnectedException, JIException {
        return opcService.getItems(groupName);
    }

    @RequestMapping("saveBlock")
    public String saveBlock(@RequestParam("blockName")String blockName,@RequestParam("outArg")String outArg,@RequestParam("inArgs")String inArgs){
        return opcService.saveBlock(blockName, outArg, inArgs);
    }

    @RequestMapping("startMonitor")
    public void startMonitor(@RequestParam("blockName")String blockName){
        opcService.startMonitor(blockName);
    }

    @RequestMapping("getBlockNames")
    public String getBlockNames(){
        return opcService.getBlockNames();
    }

    @RequestMapping("getOnlineMonitorList")
    public String getOnlineMonitorList(){
        return opcService.getOnlineMonitorList();
    }

    @RequestMapping("stopMonitor")
    public void stopMonitor(@RequestParam("blockName")String blockName){
        opcService.stopMonitor(blockName);
    }

    @RequestMapping("startMonitorAllList")
    public void startMonitorAllList(){
        opcService.startMonitorAllList();
    }

    @RequestMapping("stopMonitorAllList")
    public void stopMonitorAllList(){
        opcService.stopMonitorAllList();
    }
}
