/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.eunomia.dashboard.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <p>登录controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Value("#{'${eunomia.web.logout.url}'.trim()}")
    private String logoutUrl;
    
    @RequestMapping(value = "/logout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:" + logoutUrl;
    }

    @RequestMapping(value="/noAuthority")
    public String redirect403Error(HttpServletRequest request){
    	return "error/403";
    }

}
