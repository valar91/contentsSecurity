/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.eunomia.dashboard.interceptor;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 
 * <p>权限拦截器</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getContextPath();// contextPath=/minsu-web-troy
        String servletPath = request.getServletPath();
        // 全局设置UTF-8
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        int index = -1;
        if((index = servletPath.indexOf("?")) > -1){
            servletPath = servletPath.substring(1, index);
        }else if((index = servletPath.indexOf(".")) > -1){
            servletPath = servletPath.substring(1, index);
        }else {
            servletPath = servletPath.substring(1);
        }

        String requestType = request.getHeader("X-Requested-With");
        DataTransferObject dto=new DataTransferObject();
        UpsUserVo upsUserVo = UserUtil.getUpsUserMsg();
        //当前登录用户为空 session失效
        if(Check.NuNObj(upsUserVo)){
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {// AJAX REQUEST PROCES
                response.setHeader("responseState", "timeout");
                dto.setErrCode(1);
                dto.setMsg(servletPath+"登录超时！");
                response.getWriter().print(dto.toJsonString());
            }else{
                response.sendRedirect(contextPath + "/noAuthority");
            }
            return false;
        }

        //当前用户权限菜单列表为空
        Set<String> resUrlSet = upsUserVo.getResourceVoSet();
        if(Check.NuNCollection(resUrlSet)){
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {// AJAX REQUEST PROCES
                response.setHeader("responseState", "unauthorized");
                dto.setErrCode(1);
                dto.setMsg(servletPath+"未设置权限！");
                response.getWriter().print(dto.toJsonString());
            }else {
                response.sendRedirect(contextPath + "/noAuthority");
            }
            return false;
        }

        //当前用户未授权 
        if(!resUrlSet.contains(servletPath)){
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {// AJAX REQUEST PROCES
                response.setHeader("responseState", "unauthorized");
                dto.setErrCode(1);
                dto.setMsg(servletPath+"未设置权限！");
                response.getWriter().print(dto.toJsonString());
            }else {
                response.sendRedirect(contextPath + "/noAuthority");
            }
            return false;
        }

        return super.preHandle(request, response, handler);
        
    }
}
