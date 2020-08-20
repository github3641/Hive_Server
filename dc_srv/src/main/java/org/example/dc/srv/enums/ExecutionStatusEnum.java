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
    RUNNING(100, "running"),
    SUCCESS(200, "success"),
    FAILED(300, "failed"),;
    private int code;
    private String msg;

    ExecutionStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
