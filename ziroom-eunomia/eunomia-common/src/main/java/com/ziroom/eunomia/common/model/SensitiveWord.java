package com.ziroom.eunomia.common.model;

import lombok.*;

import java.io.Serializable;

/**
 * <p>敏感词</p>
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SensitiveWord implements Serializable {

    private static final long serialVersionUID = 404919969481338034L;

    private String fid;

    private Integer key;

    private String word;

    // todo dashboard enum move to common, and use enum!!
    private Integer status;

    private Integer Type;

    private boolean enabled;

}
