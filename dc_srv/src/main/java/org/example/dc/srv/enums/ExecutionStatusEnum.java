package org.example.dc.srv.enums;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.enums
 * Class: ExecutionStatusEnum
 * Author: RuiChao Lv
 * Date: 2020/8/18
 * Version: 1.0
 * Description:
 */
public enum ExecutionStatusEnum {
    RUNNING("running"),
    SUCCESS("success"),
    FAILED("failed"),;
    private String msg;

    ExecutionStatusEnum( String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
