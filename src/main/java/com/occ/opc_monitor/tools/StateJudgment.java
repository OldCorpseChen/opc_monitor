package com.occ.opc_monitor.tools;

public class StateJudgment {


   //   ＿|￣
    public static boolean UP_EDGE(boolean oldArg,boolean newArg){
        return !oldArg && newArg;
    }

    //  ￣|＿
    public static boolean DOWN_EDGE(boolean oldArg,boolean newArg){
        return oldArg && !newArg;
    }
}
