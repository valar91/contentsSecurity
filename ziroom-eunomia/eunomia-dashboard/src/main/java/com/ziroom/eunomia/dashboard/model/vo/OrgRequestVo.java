package com.ziroom.eunomia.dashboard.model.vo;

import com.ziroom.eunomia.dashboard.model.entity.OrgRequestEntity;
import lombok.Data;

@Data
public class OrgRequestVo extends OrgRequestEntity{

    /**
     * 应用名称
     */
    private String orgName;

    /**
     * 应用域名
     */
    private String orgDomain;

}