package com.ziroom.eunomia.dashboard.web;

import com.asura.framework.base.util.Check;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * <p>首页controller</p>
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
@RequestMapping("/")
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    /**
     * 静态资源地址
     */
    @Value("${eunomia.static.resource.url}")
    private String staticResourceUrl;

    @Value("#{'${login.error.msg}'.trim()}")
    private String loginErrorMsg;

    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("staticResourceUrl", staticResourceUrl);
        //判断用户是否存在
        UpsUserVo upsUserVo = UserUtil.getUpsUserMsg();
        if (Check.NuNObj(upsUserVo)) {
            request.setAttribute("errorMsg", loginErrorMsg);
            return "/error/loginerror";
        }

        List<ResourceVo> currentuserResList = upsUserVo.getResourceVoList();

        if(Check.NuNCollection(currentuserResList)){
            request.setAttribute("errorMsg", loginErrorMsg);
            return "/error/loginerror";
        }
        request.setAttribute("currentuserResList", currentuserResList);

        return "/index/index";
    }


}
