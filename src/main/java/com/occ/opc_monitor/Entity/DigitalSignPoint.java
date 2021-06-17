package com.occ.opc_monitor.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DigitalSignPoint {
    private String signName;
    private boolean sign;

    public DigitalSignPoint setSignName(String signName) {
        this.signName = signName;
        return this;
    }

    public DigitalSignPoint setSign(boolean sign) {
        this.sign = sign;
        return this;
    }

    @Override
    public String toString() {
        return "DigitalSignPoint{" +
                "signName='" + signName + '\'' +
                ", sign=" + sign +
                '}';
    }
}
