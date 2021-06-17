package com.occ.opc_monitor.service.serviceImpl;

import com.google.gson.Gson;
import com.occ.opc_monitor.Entity.BlockModel;
import com.occ.opc_monitor.Entity.Group;
import com.occ.opc_monitor.config.opcConfig;
import com.occ.opc_monitor.config.springConfig;
import com.occ.opc_monitor.service.opcService;
import com.occ.opc_monitor.tools.AccessThread;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.*;

@Service
public class opcServiceImpl implements opcService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private opcConfig opcConfig;


    private final static String BLOCK="Block";

    private Logger logger= LoggerFactory.getLogger(opcServiceImpl.class);

    public String getGroups() throws UnknownHostException, AlreadyConnectedException, JIException {
        Server server= opcConfig.opc_server();
        List<Group> groups=new ArrayList<>();
        dumpFlat_groups(server.getFlatBrowser(),groups);
        return gson.toJson(groups);
    }

    public String getItems(String groupName) throws UnknownHostException, AlreadyConnectedException, JIException {
        Server server= opcConfig.opc_server();
        List<String> items=new ArrayList<>();
        dumpFlat_items(server.getFlatBrowser(),items,groupName);
        System.out.println(groupName);
        return gson.toJson(new Group().setGroupName(groupName).setItems(items));
    }


    public String saveBlock(String blockName,String outArg,String inArgs){
        redisTemplate.opsForHash().put(BLOCK,blockName+":"+outArg,inArgs);
        return "true";
    }

    public void startMonitor(String blockName){
        if(!springConfig.threadHashMap.containsKey(blockName.split(":")[0])){
            String inArgs= Objects.requireNonNull(redisTemplate.opsForHash().get(BLOCK, blockName)).toString();
            AccessThread accessThread=new AccessThread(new BlockModel(blockName.split(":")[1], new ArrayList<>(Arrays.asList(inArgs.split(":")))),true,opcConfig.opc_server());
            logger.info("[{}]开始监控[{}]",accessThread.getName(),blockName.split(":")[0]);
            springConfig.threadHashMap.put(blockName.split(":")[0], accessThread);
            accessThread.start();
        }
    }

    public void startMonitorAllList(){
        Set<String> blockNames=redisTemplate.opsForHash().keys(BLOCK);
        for(String s:blockNames){
            startMonitor(s);
        }
    }

    @Override
    public void stopMonitorAllList() {
        HashMap<String, AccessThread> map = new HashMap<>(springConfig.threadHashMap);
        for(Map.Entry<String,AccessThread> item:map.entrySet()){
            stopMonitor(item.getKey());
        }
    }

    public String getBlockNames(){
        Set<String> blockNames=redisTemplate.opsForHash().keys(BLOCK);
        return gson.toJson(blockNames);
    }

    public String getOnlineMonitorList(){
        return gson.toJson(springConfig.threadHashMap.keySet());
    }

    public void stopMonitor(String blockName){
        if(null!=blockName&&springConfig.threadHashMap.containsKey(blockName.split(":")[0])){
            logger.info("[{}]线程停止监控[{}]",springConfig.threadHashMap.get(blockName).getName(),blockName);
            springConfig.threadHashMap.get(blockName).setFlag(false);
            springConfig.threadHashMap.remove(blockName);
        }


    }

    private static void dumpFlat_groups(final FlatBrowser browser,List<Group> groups) throws IllegalArgumentException, UnknownHostException, JIException {
        for (String name : browser.browse()) {
            if(name.split("\\.").length==3){
                groups.add(new Group().setGroupName(name));
            }
        }
    }



    private static void dumpFlat_items(final FlatBrowser browser,List<String> items,String groupName) throws IllegalArgumentException, UnknownHostException, JIException {
        for (String name : browser.browse()) {
            if(name.split("\\.").length>=4&&groupName.equals(name.split("\\.")[1])){
                items.add(name);
            }
        }
    }

    private static void dumpTree(final Branch branch, final int level) {

        for (final Leaf leaf : branch.getLeaves()) {
            dumpLeaf(leaf, level);
        }
        for (final Branch subBranch : branch.getBranches()) {
            dumpBranch(subBranch, level);
            dumpTree(subBranch, level + 1);
        }
    }

    private static String printTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    private static void dumpLeaf(final Leaf leaf, final int level) {
        System.out.println(printTab(level) + "Leaf: " + leaf.getName() + ":"
                + leaf.getItemId());
    }

    private static void dumpBranch(final Branch branch, final int level) {
        System.out.println(printTab(level) + "Branch: " + branch.getName());
    }

    public static void dumpItem(Item item) throws JIException {
        System.out.println("[" + (++count) + "],ItemName:[" + item.getId()
                + "],value:" + item.read(false).getValue());
    }

    private static int count;

}
