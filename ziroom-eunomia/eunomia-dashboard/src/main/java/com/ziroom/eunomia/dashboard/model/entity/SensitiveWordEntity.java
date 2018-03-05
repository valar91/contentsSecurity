package com.ziroom.eunomia.dashboard.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * <p>敏感词实体</p>
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
public class SensitiveWordEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
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
     * 是否启用（0-待审核，1-审核通过，2-审核驳回）
     */
    private Integer sensitiveWordStatus;

    /**
     * 创建时间
     */
    private Timestamp createDate;

    /**
     * 最后更新时间
     */
    private Timestamp lastModifyDate;

    /**
     * 是否删除(0-否 1-是)
     */
    private Integer isDel;

    @JsonIgnore
    private static final List<Integer> DISABLED_STATUS = Arrays.asList(-1,0,1,2);

    public boolean enabled() {
        return this.sensitiveWordStatus == 1;// 1是审核通过
    }

    public boolean isBlank() {
        return StringUtils.isBlank(this.content);
    }
}