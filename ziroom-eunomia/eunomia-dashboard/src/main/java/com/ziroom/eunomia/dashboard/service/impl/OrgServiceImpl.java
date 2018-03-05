package com.ziroom.eunomia.dashboard.service.impl;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ziroom.eunomia.dashboard.config.db.ReadOnly;
import com.ziroom.eunomia.dashboard.mapper.OrgMapper;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.model.valenum.OrgLevelEnum;
import com.ziroom.eunomia.dashboard.model.valenum.OrgStatusEnum;
import com.ziroom.eunomia.dashboard.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>应用方处理</p>
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
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgMapper orgMapper;

    @Transactional
    @Override
    public int addOrg(OrgEntity orgEntity) {


        orgEntity.setFid(UUIDGenerator.hexUUID());
        // TODO 先按uuid生成密钥
        orgEntity.setOrgSecret(UUIDGenerator.hexUUID());
        // TODO 暂时只能添加 应用部门 的层级
        orgEntity.setOrgLevel(OrgLevelEnum.APP_DEP.getCode());
        // 新增默认开始授权
        orgEntity.setOrgStatus(OrgStatusEnum.ENABLE.getCode());
        return orgMapper.addOrg(orgEntity);
    }

    @ReadOnly
    @Override
    public Page<OrgEntity> selectByPage(OrgRequest orgRequest) {
        // TODO 暂时只能查询 应用部门 的层级
        orgRequest.setOrgLevel(OrgLevelEnum.APP_DEP.getCode());
        PageHelper.startPage(orgRequest.getPage(), orgRequest.getLimit());
        return orgMapper.selectByPage(orgRequest);
    }

    @ReadOnly
    @Override
    public List<OrgEntity> selectAll() {
        // TODO 暂时只能查询 应用部门 的层级
        OrgRequest orgRequest = new OrgRequest();
        orgRequest.setOrgLevel(OrgLevelEnum.APP_DEP.getCode());
        return orgMapper.selectAll(orgRequest);
    }

    @Transactional
    @Override
    public int updateOrg(OrgEntity orgEntity) {
        if (!Check.NuNStr(orgEntity.getFid())) {
            return orgMapper.updateOrg(orgEntity);
        } else {
            return -1;
        }
    }

    @ReadOnly
    @Override
    public OrgEntity findByUsername(String username) {
        return orgMapper.selectByUsername(username);
    }

    @ReadOnly
    @Override
    public OrgEntity findByFid(String fid) {
        return orgMapper.selectByFid(fid);
    }
}
