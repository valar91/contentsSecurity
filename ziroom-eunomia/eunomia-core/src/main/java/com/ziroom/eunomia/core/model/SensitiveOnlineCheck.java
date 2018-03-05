package com.ziroom.eunomia.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月14日 16:40
 * @Version 1.0
 * @Since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveOnlineCheck implements Serializable {

    private static final long serialVersionUID = 5695037421030362836L;

    private String empName;

    private String empCode;

    private String content;

    private List<SegmentWord> segmentWords;

    private String currentTime;

    private String topic;
}
