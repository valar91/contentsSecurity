package com.ziroom.eunomia.dashboard.model.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>请求应用实体</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年11月09日 14:27
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class OrgEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 应用名称
     */
    private String orgName;

    /**
     * 应用域名
     */
    private String orgDomain;

    /**
     * 组织用户名
     */
    private String orgUsername;

    /**
     * 应用层级
     */
    private Integer orgLevel;

    /**
     * 父级fid
     */
    private String parentFid;

    /**
     * 授权码
     */
    private String orgSecret;

    /**
     * 授权状态（0-开启，1-关闭）
     */
    private Integer orgStatus;

    /**
     * 是否删除(0-否 1-是)
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Timestamp createDate;

    /**
     * 最后更新时间
     */
    private Timestamp lastModifyDate;

}
