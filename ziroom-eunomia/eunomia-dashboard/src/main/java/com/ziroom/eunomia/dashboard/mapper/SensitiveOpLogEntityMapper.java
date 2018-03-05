package com.ziroom.eunomia.dashboard.mapper;

import com.ziroom.eunomia.dashboard.model.entity.SensitiveOpLogEntity;

/**
 * <p>敏感词操作日志mapper</p>
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
public interface SensitiveOpLogEntityMapper {

    int insert(SensitiveOpLogEntity sensitiveOpLogEntity);

}