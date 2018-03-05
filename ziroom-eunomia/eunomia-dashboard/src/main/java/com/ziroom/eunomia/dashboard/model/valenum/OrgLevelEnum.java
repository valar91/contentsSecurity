package com.ziroom.eunomia.dashboard.model.valenum;

public enum OrgLevelEnum {

    COMPANY(0, "公司"),
    PLATFORM(1, "平台"),
    APP_DEP(2, "应用部门");

    OrgLevelEnum(int code, String name) {
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
