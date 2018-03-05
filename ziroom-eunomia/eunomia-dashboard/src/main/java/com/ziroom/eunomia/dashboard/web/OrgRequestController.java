package com.ziroom.eunomia.dashboard.web;

import com.asura.framework.base.util.Check;
import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.dto.Result;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.model.valenum.OrgStatusEnum;
import com.ziroom.eunomia.dashboard.model.vo.OrgRequestVo;
import com.ziroom.eunomia.dashboard.service.OrgRequestService;
import com.ziroom.eunomia.dashboard.service.OrgService;
import com.ziroom.minsu.services.common.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>应用管理</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年11月01日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/org/request")
public class OrgRequestController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRequestService orgRequestService;

    @RequestMapping(value = "/list")
    public String requestList(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("orgList", orgService.selectAll());
        return "orgRequest/list";
    }

    /**
     *
     * 分页查询调用记录
     *
     * @author zhangyl2
     * @created 2017年11月02日 12:56
     * @param
     * @return
     */
    @RequestMapping(value = "/selectByPage")
    @ResponseBody
    public PageResult selectByPage(OrgRequest orgRequest) {
        Page<OrgRequestVo> list = orgRequestService.selectByPage(orgRequest);

        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(list.getTotal());
        return pageResult;
    }

}