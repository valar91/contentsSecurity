package com.ziroom.eunomia.dashboard.mapper;

import com.ziroom.eunomia.dashboard.model.entity.OrgSensitiveResultEntity;

import java.util.List;

/**
 * <p>应用方文本敏感校验结果mapper</p>
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
public interface OrgSensitiveResultEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrgSensitiveResultEntity record);

    int insertSelective(OrgSensitiveResultEntity record);

    OrgSensitiveResultEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrgSensitiveResultEntity record);

    int updateByPrimaryKey(OrgSensitiveResultEntity record);

    Integer insertBatch(List<OrgSensitiveResultEntity> orgSensitiveResultEntities);
}