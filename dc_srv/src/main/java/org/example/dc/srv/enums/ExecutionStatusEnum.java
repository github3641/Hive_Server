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
    RUNNING("000", "running"),
    SUCCESS("001", "success"),
    FAILED("002", "failed"),;
    private String code;
    private String msg;

    ExecutionStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
