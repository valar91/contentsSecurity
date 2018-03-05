package com.ziroom.eunomia.dashboard.model.valenum;

public enum OrgStatusEnum {

    ENABLE(0, "已授权"),
    DISABLE(1, "未授权");

    OrgStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
