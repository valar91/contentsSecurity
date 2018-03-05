package com.ziroom.eunomia.dashboard.service.impl;

import com.ziroom.eunomia.common.exception.SensitiveException;
import com.ziroom.eunomia.dashboard.mapper.OrgSensitiveResultEntityMapper;
import com.ziroom.eunomia.dashboard.model.entity.OrgSensitiveResultEntity;
import com.ziroom.eunomia.dashboard.service.OrgSensitiveResultService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>应用请求操作处理</p>
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
@Service
public class OrgSensitiveResultServiceImpl implements OrgSensitiveResultService {

    @Autowired
    private OrgSensitiveResultEntityMapper orgSensitiveResultEntityMapper;

    @Override
    public Integer insertBatch(List<OrgSensitiveResultEntity> orgSensitiveResultEntities) {

        if (CollectionUtils.isEmpty(orgSensitiveResultEntities)) {
            throw new SensitiveException("seneitive word result not found!");
        }

        Integer flag = orgSensitiveResultEntityMapper.insertBatch(orgSensitiveResultEntities);

        return flag;
    }



}
