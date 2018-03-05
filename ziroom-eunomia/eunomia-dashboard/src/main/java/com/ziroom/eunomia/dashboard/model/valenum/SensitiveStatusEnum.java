package com.ziroom.eunomia.dashboard.model.valenum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SensitiveStatusEnum {

    PENDING_AUDIT(0, "待审核"),
    AUDIT_PASSED(1, "审核通过"),
    AUDIT_REJECTION(2, "审核驳回");

    SensitiveStatusEnum(int code, String name) {
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

    public static String getNameBycode(int code){
        for (SensitiveStatusEnum status : SensitiveStatusEnum.values()) {
            if (status.getCode() == code) {
                return status.getName();
            }
        }
        return "";
    }

    public static List<Map<String, Object>> transToListMap(){
        List<Map<String, Object>> list = new ArrayList<>();
        for (SensitiveStatusEnum status : SensitiveStatusEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", status.code);
            map.put("name", status.name);
            list.add(map);
        }
        return list;
    }
}
