package com.ziroom.eunomia.dashboard.model.vo;

import com.ziroom.eunomia.core.model.SegmentWord;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>敏感词Vo</p>
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
public class SensitiveVo implements Serializable {

    private static final long serialVersionUID = 4077133988836662799L;

    private boolean isSensitive;

    private List<SegmentWord> ikList;

}
