package com.ziroom.eunomia.dashboard.web;

import com.asura.framework.base.util.Check;
import com.github.pagehelper.Page;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import com.ziroom.eunomia.dashboard.model.dto.Result;
import com.ziroom.eunomia.dashboard.model.dto.SensitiveWordRequest;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
import com.ziroom.eunomia.dashboard.model.valenum.SensitiveStatusEnum;
import com.ziroom.eunomia.dashboard.model.valenum.SensitiveTypeEnum;
import com.ziroom.eunomia.dashboard.service.SensitiveWordService;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.services.common.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>敏感词库管理</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年11月01日
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/sensitiveWord")
public class SensitiveWordController {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    /**
     * 所有敏感词列表
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月02日 13:01
     */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, HttpServletResponse response) {

        // TODO thymeleaf暂时没有类似jsptagsupport的解决方案，先简单处理控制按钮权限
        request.setAttribute("forceDelete", UserUtil.checkResourceAuth("sensitiveWord/forceDelete"));
        request.setAttribute("typeList", SensitiveTypeEnum.transToListMap());
        request.setAttribute("statusList", SensitiveStatusEnum.transToListMap());
        return "sensitiveWord/list";
    }


    /**
     * 新增敏感词
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 19:32
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(SensitiveWordEntity sensitiveWordEntity) {
        Result result = new Result();
        if (Check.NuNObj(SensitiveTypeEnum.getNameBycode(sensitiveWordEntity.getSensitiveWordType()))
                || Check.NuNStr(sensitiveWordEntity.getContent())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }

        int addResult = sensitiveWordService.addSensitiveWord(sensitiveWordEntity);

        if (addResult == 1) {
            return result.setData(addResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("新增失败");
        }
    }

    /**
     * 修改敏感词
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 18:23
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(SensitiveWordEntity sensitiveWordEntity) {
        Result result = new Result();
        if (Check.NuNStr(sensitiveWordEntity.getFid())
                || Check.NuNObj(sensitiveWordEntity.getSensitiveWordType())
                || Check.NuNObj(sensitiveWordEntity.getContent())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }
        int auditResult = sensitiveWordService.updateSensitiveWord(sensitiveWordEntity);
        if (auditResult == 1) {
            return result.setData(auditResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("修改失败");
        }
    }

    /**
     * 非审核状态，正常删除敏感词
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 19:32
     */
    @RequestMapping(value = "/normalDelete")
    @ResponseBody
    public Result normalDelete(SensitiveWordEntity sensitiveWordEntity) {
        Result result = new Result();
        if (Check.NuNStr(sensitiveWordEntity.getFid())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }

        int deleteResult = sensitiveWordService.deleteSensitiveWord(sensitiveWordEntity, false);
        if (deleteResult == 1) {
            return result.setData(deleteResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("删除失败");
        }
    }

    /**
     * 无条件删除敏感词
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 19:32
     */
    @RequestMapping(value = "/forceDelete")
    @ResponseBody
    public Result forceDelete(SensitiveWordEntity sensitiveWordEntity) {
        Result result = new Result();
        if (Check.NuNStr(sensitiveWordEntity.getFid())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }

        int deleteResult = sensitiveWordService.deleteSensitiveWord(sensitiveWordEntity, true);
        if (deleteResult == 1) {
            return result.setData(deleteResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("删除失败");
        }
    }

    /**
     * 分页查询词库
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 19:32
     */
    @RequestMapping(value = "/selectByPage")
    @ResponseBody
    public PageResult selectByPage(SensitiveWordRequest sensitiveWordRequest) {
        Page<SensitiveWordEntity> list = sensitiveWordService.selectByPage(sensitiveWordRequest);

        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(list.getTotal());
        return pageResult;
    }

    /**
     * 待审核敏感词列表
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月02日 13:01
     */
    @RequestMapping(value = "/auditList")
    public String auditList(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("typeList", SensitiveTypeEnum.transToListMap());
        request.setAttribute("statusList", SensitiveStatusEnum.transToListMap());
        return "sensitiveWord/auditList";
    }

    /**
     * 分页查询待审核敏感词列表
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 19:32
     */
    @RequestMapping(value = "/selectAuditList")
    @ResponseBody
    public PageResult selectAuditList(SensitiveWordRequest sensitiveWordRequest) {

        Page<SensitiveWordEntity> list = sensitiveWordService.selectByPage(sensitiveWordRequest);

        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(list.getTotal());
        return pageResult;
    }

    /**
     * 审核敏感词，更新其状态
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 18:23
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public Result audit(SensitiveWordEntity sensitiveWordEntity) {
        Result result = new Result();
        if (Check.NuNStr(sensitiveWordEntity.getFid())
                || Check.NuNObj(sensitiveWordEntity.getSensitiveWordStatus())) {
            return result.setStatus(Result.ERROR).setMessage("参数错误");
        }
        int auditResult = sensitiveWordService.auditSensitiveWord(sensitiveWordEntity);
        if (auditResult == 1) {
            return result.setData(auditResult);
        } else {
            return result.setStatus(Result.ERROR).setMessage("审核操作失败");
        }
    }

}