package com.ziroom.eunomia.dashboard.model.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>敏感词操作(审核)日志实体</p>
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
public class SensitiveOpLogEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 敏感词fid
     */
    private String sensitiveWordFid;

    /**
     * 操作员工编号
     */
    private String empCode;

    /**
     * 操作员工姓名
     */
    private String empName;

    /**
     * 初始状态
     */
    private Integer fromStatus;

    /**
     * 修改后状态
     */
    private Integer toStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp createDate;

}