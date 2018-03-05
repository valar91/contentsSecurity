package com.ziroom.eunomia.dashboard.model.valenum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SensitiveTypeEnum {

    YELLOW_REACTIONARY(1, "黄反词"),
    ADVERTISEMENT(2, "广告词"),
    COMPETING_PRODUCTS(3, "竞品词");

    SensitiveTypeEnum(int code, String name) {
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
        for (SensitiveTypeEnum type : SensitiveTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return "";
    }

    public static List<Map<String, Object>> transToListMap(){
        List<Map<String, Object>> list = new ArrayList<>();
        for (SensitiveTypeEnum type : SensitiveTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", type.code);
            map.put("name", type.name);
            list.add(map);
        }
        return list;
    }
}
