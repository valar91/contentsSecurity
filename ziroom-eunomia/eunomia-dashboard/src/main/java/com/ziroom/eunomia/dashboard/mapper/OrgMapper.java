package com.ziroom.eunomia.dashboard.mapper;

import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;

import java.util.List;

/**
 * <p>应用方mapper</p>
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
public interface OrgMapper {

    Page<OrgEntity> selectByPage(OrgRequest orgRequest);

    List<OrgEntity> selectAll(OrgRequest orgRequest);

    int addOrg(OrgEntity orgEntity);

    int updateOrg(OrgEntity orgEntity);

    OrgEntity selectByUsername(String username);

    OrgEntity selectByFid(String fid);
}