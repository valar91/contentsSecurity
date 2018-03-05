package com.ziroom.eunomia.dashboard.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * <p>应用方文本校验结果实体</p>
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
@NoArgsConstructor
@AllArgsConstructor
public class OrgSensitiveResultEntity {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 调用记录fid
     */
    private String requestFid;

    /**
     * 敏感词fid
     */
    private String sensitiveWordFid;

    /**
     * 敏感词
     */
    private String sensitiveWord;

    /**
     * 敏感词出现次数
     */
    private Integer sensitiveCount;

    /**
     * 创建时间
     */
    private Timestamp createDate;

}