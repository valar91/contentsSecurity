package com.ziroom.eunomia.dashboard.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ziroom.eunomia.dashboard.mapper.OrgRequestEntityMapper;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.vo.OrgRequestVo;
import com.ziroom.eunomia.dashboard.service.OrgRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>应用方请求逻辑</p>
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
@Slf4j
@Service
@Transactional
public class OrgRequestServiceImpl implements OrgRequestService {

    @Autowired
    private OrgRequestEntityMapper orgRequestEntityMapper;

    @Override
    public Page<OrgRequestVo> selectByPage(OrgRequest orgRequest) {
        PageHelper.startPage(orgRequest.getPage(), orgRequest.getLimit());
        return orgRequestEntityMapper.selectByPage(orgRequest);
    }
}
