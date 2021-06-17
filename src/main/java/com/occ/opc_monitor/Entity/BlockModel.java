package com.occ.opc_monitor.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BlockModel {
    private String outArgs;
    private List<String> inArgs;

    public BlockModel setOutArgs(String outArgs) {
        this.outArgs = outArgs;
        return this;
    }

    public BlockModel setInArgs(List<String> inArgs) {
        this.inArgs = inArgs;
        return this;
    }
}
