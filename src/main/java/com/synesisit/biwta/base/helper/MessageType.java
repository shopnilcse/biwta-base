package com.synesisit.biwta.base.helper;

import lombok.Getter;

@Getter
public enum MessageType {
    SUCCESS, FAIL, ERROR;

    String status;
}
