package com.ziroom.eunomia.dashboard.service;

import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;

import java.util.List;

/**
 * <p>组织机构处理</p>
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
public interface OrgService {

    int addOrg(OrgEntity orgEntity);

    Page<OrgEntity> selectByPage(OrgRequest orgRequest);

    List<OrgEntity> selectAll();

    int updateOrg(OrgEntity orgEntity);

    OrgEntity findByUsername(String username);

    OrgEntity findByFid(String fid);
}
