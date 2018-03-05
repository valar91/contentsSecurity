package com.ziroom.eunomia.dashboard.service;

import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.SensitiveWordRequest;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>敏感词处理</p>
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
public interface SensitiveWordService {

    int addSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    int updateSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    int deleteSensitiveWord(SensitiveWordEntity sensitiveWordEntity, boolean forceDelete);

    Page<SensitiveWordEntity> selectByPage(SensitiveWordRequest sensitiveWordRequest);

    int auditSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    /**
     * 查询全部敏感词
     * @return
     */
    List<SensitiveWordEntity> selectAll();

    /**
     * 查询修改时间范围内的敏感词
     * @param start
     * @param end
     * @return
     */
    List<SensitiveWordEntity> selectByLastModiyTime(Timestamp start,Timestamp end);
}
