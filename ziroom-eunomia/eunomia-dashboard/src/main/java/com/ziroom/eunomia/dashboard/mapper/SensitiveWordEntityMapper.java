package com.ziroom.eunomia.dashboard.mapper;

import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.SensitiveWordRequest;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>敏感词词库mapper</p>
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
public interface SensitiveWordEntityMapper {

    int addSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    int updateSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    int deleteSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    SensitiveWordEntity selectOne(String fid);

    Page<SensitiveWordEntity> selectByPage(SensitiveWordRequest sensitiveWordRequest);

    int auditSensitiveWord(SensitiveWordEntity sensitiveWordEntity);

    List<SensitiveWordEntity> selectByLastModifytime(@Param("start") Timestamp start, @Param("end") Timestamp end);

    List<SensitiveWordEntity> selectAll();
}