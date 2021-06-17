package com.occ.opc_monitor.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Group {
    private String groupName;
    private List<String> items;

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Group setItems(List<String> items) {
        this.items = items;
        return this;
    }
}
