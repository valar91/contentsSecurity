package com.ziroom.eunomia.dashboard.service;

import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.model.vo.OrgRequestVo;

/**
 * <p>应用请求</p>
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
public interface OrgRequestService {

    Page<OrgRequestVo> selectByPage(OrgRequest orgRequest);

}
