package com.ziroom.eunomia.dashboard.service;

import com.ziroom.eunomia.dashboard.model.entity.OrgSensitiveResultEntity;

import java.util.List;

/**
 * <p>应用请求-敏感词文本处理</p>
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
public interface OrgSensitiveResultService {

    Integer insertBatch(List<OrgSensitiveResultEntity> orgSensitiveResultEntities) throws Exception;
}
