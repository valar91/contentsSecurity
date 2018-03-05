package com.ziroom.eunomia.dashboard.web;

import com.asura.framework.base.util.Check;
import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.model.dto.OrgRequest;
import com.ziroom.eunomia.dashboard.model.dto.Result;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.model.valenum.OrgStatusEnum;
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
@RequestMapping(value = "/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("statusList", OrgStatusEnum.values());
        return "org/list";
    }

    /**
     * 
     * 添加应用
     * 
     * @author zhangyl2
     * @created 2017年11月10日 14:44
     * @param 
     * @return 
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(OrgEntity orgEntity) {
        Result result = new Result();
        if(Check.NuNStr(orgEntity.getOrgName())
                || Check.NuNStr(orgEntity.getOrgDomain())
                || Check.NuNStr(orgEntity.getOrgUsername())){
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }

        int addResult = orgService.addOrg(orgEntity);
        if (addResult == 1) {
            return result.setData(addResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("添加失败");
        }
    }

    /**
     *
     * 分页查询授权组织、应用
     * 目前先查询应用部门，后期页面考虑用树结构展示+左子页面的方式
     * 
     * @author zhangyl2
     * @created 2017年11月02日 12:56
     * @param 
     * @return 
     */
    @RequestMapping(value = "/selectByPage")
    @ResponseBody
    public PageResult selectByPage(OrgRequest orgRequest) {
        Page<OrgEntity> list = orgService.selectByPage(orgRequest);

        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(list.getTotal());
        return pageResult;
    }

    /**
     * 
     * 修改授权
     * 
     * @author zhangyl2
     * @created 2017年11月02日 12:59
     * @param 
     * @return 
     */
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public Result updateStatus(OrgEntity orgEntity) {
        Result result = new Result();
        if (Check.NuNStr(orgEntity.getFid())
                || Check.NuNObj(orgEntity.getOrgStatus())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }

        int updateResult = orgService.updateOrg(orgEntity);
        if (updateResult == 1) {
            return result.setData(updateResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("操作失败");
        }
    }


}