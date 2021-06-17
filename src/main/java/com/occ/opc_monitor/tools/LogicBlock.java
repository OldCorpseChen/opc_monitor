package com.occ.opc_monitor.tools;

import com.google.gson.Gson;
import com.occ.opc_monitor.Entity.DigitalSignPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogicBlock {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Gson gson;


    public static List<Integer> AND_Block(boolean outArg, boolean outSign, int outArgCount , List<DigitalSignPoint> inArgs) {
        List<Integer> count=new ArrayList<>();

        if(outSign){
            if(!outArg){
                for(int i=0;i<inArgs.size();i++){
                    if(i!=outArgCount){
                        if(!inArgs.get(i).isSign()){
                            count.add(i);
                        }
                    }
                }
            }
        }
        return count;
    }

    public static List<Integer> OR_Block(boolean outArg, boolean outSign, boolean... inArgs) {
        List<Integer> count=new ArrayList<>();
        if(outSign){

        }
        return count;
    }
}
