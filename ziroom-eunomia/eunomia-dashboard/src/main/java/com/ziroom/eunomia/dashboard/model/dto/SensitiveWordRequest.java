package com.ziroom.eunomia.dashboard.model.dto;

import lombok.Data;

@Data
public class SensitiveWordRequest extends PageRequest {

    private String fid;

    /**
     * 词类型（1-黄反词，2-广告词，3-竞品词）
     */
    private Integer sensitiveWordType;

    /**
     * 词文本
     */
    private String content;

    /**
     * 状态
     */
    private Integer sensitiveWordStatus;

}
