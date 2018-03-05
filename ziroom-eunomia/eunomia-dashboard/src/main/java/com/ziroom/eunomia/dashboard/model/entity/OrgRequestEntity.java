package com.ziroom.eunomia.dashboard.model.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>应用方请求实体</p>
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrgRequestEntity {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 应用方业务Id
     */
    private String orgFid;

    /**
     * 授权应用查询文本
     */
    private String content;

    /**
     * 创建时间
     */
    private Timestamp createDate;


}