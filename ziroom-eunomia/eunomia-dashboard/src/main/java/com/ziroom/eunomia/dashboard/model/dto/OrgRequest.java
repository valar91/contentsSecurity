package com.ziroom.eunomia.dashboard.model.dto;

import lombok.Data;

@Data
public class OrgRequest extends PageRequest {

    /**
     * 逻辑fid
     */
    private String orgFid;

    /**
     * 应用名称
     */
    private String orgName;

    /**
     * 应用域名
     */
    private String orgDomain;

    /**
     * 应用层级
     */
    private Integer orgLevel;

    /**
     * 授权状态（0-开启，1-关闭）
     */
    private Integer orgStatus;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

}
