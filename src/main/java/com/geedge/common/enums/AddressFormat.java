package com.geedge.common.enums;

/**
 * TODO
 *
 * @Classname addrFormat
 * @Date 2024/1/3 16:26
 * @Author wWei
 */

import lombok.Getter;

@Getter
public enum AddressFormat {
    SINGLE("Single"),
    RANGE("Range"),
    CIDR("CIDR"),
    ;
    private final String value;

    AddressFormat(String value) {
        this.value = value;
    }
}
