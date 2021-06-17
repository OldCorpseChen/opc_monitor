package com.occ.opc_monitor.tools;

import com.occ.opc_monitor.Entity.BlockModel;
import com.occ.opc_monitor.Entity.DigitalSignPoint;
import lombok.SneakyThrows;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.*;

public class AccessThread extends Thread{

    private boolean flag=true;
    private Server server;
    private BlockModel blockModel;
    private boolean outSign;

    private Logger logger= LoggerFactory.getLogger(AccessThread.class);
    public AccessThread(BlockModel blockModel,boolean outSign,Server server){
        this.blockModel=blockModel;
        this.outSign=outSign;
        this.server=server;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @SneakyThrows
    @Override
    public void run() {
        autoReconnect(blockModel);
    }

    private void autoReconnect(BlockModel blockModel) throws UnknownHostException, AlreadyConnectedException, JIException, DuplicateGroupException, NotConnectedException, AddFailedException, InterruptedException {
//        server.connect();
        boolean oldArg=true;
        List<DigitalSignPoint> Args=new ArrayList<>();
        Group group = server.addGroup();
        List<String> list=new ArrayList<>();
        list.add(blockModel.getOutArgs());
        list.addAll(blockModel.getInArgs());
        Set<String> warningSign=new HashSet<>();
        Map<String, Item> items = group.addItems(list.toArray(new String[0]));
        int countt=0;
        while (flag){
            Args.clear();
            for (Map.Entry<String, Item> temp : items.entrySet()) {
                Args.add(new DigitalSignPoint(temp.getKey(),temp.getValue().read(false).getValue().getObjectAsBoolean()));

            }
            for(int i=0;i<Args.size();i++){
                if(Args.get(i).getSignName().equals(blockModel.getOutArgs())){
                    countt=i;
                }
            }


            if(StateJudgment.UP_EDGE(oldArg, Args.get(countt).isSign())){
                logger.info(Args.get(countt).getSignName()+"恢复正常");
            }else if(StateJudgment.DOWN_EDGE(oldArg, Args.get(countt).isSign())){
                warningSign.clear();
                List<Integer> counts=LogicBlock.AND_Block(Args.get(countt).isSign(), outSign, countt,Args);
                for (Integer count : counts) {
                    warningSign.add(Args.get(count).getSignName());
                }
                logger.warn(warningSign.toString());
            }
            oldArg=Args.get(countt).isSign();
            Thread.sleep(200);
        }
    }
}
