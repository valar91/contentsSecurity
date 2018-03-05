/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.eunomia.dashboard.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.eunomia.dashboard.config.Constants;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.services.common.entity.UpsConfig;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>自动根据单点登录系统的信息设置本系统的用户信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author bushujie
 * @version 1.0
 * @since 1.0
 */
public class AutoSetUserAdapterFilter implements Filter {

    private WebApplicationContext webApplicationContext;

    public AutoSetUserAdapterFilter() {
    }

    public void destroy() {
    }

    /**
     * 过滤逻辑：首先判断单点登录的账户是否已经存在本系统中，
     * 如果不存在使用用户查询接口查询出用户对象并设置在Session中
     *
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 当前登录用户
        UpsUserVo upsUserVo = UserUtil.getUpsUserMsg();
        // currentLoginUserName为空时是未登录状态，否则为登录状态
        if (null == upsUserVo) {
            // _const_cas_assertion_是CAS中存放登录用户名的session标志
            Object object = httpRequest.getSession().getAttribute(Constants.CONST_CAS_ASSERTION);
            // 判断CAS存到客户端session中的用户名是否为空
            if (null != object) {
                Assertion assertion = (Assertion) object;
                String loginName = assertion.getPrincipal().getName();

                //服务查询登录用户信息
                UpsConfig upsConfig = webApplicationContext.getBean(UpsConfig.class);
                Map<String, String> param = new HashMap<>();
                param.put("userAccount", loginName);
                param.put("sysCode", upsConfig.getSystemCode());
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                String resultJson = CloseableHttpUtil.sendFormPost(upsConfig.getUserMsgApi(), param, map);

                // 请求结果
                if (!SOAResParseUtil.checkSOAReturnSuccess(resultJson)) {
                    httpResponse.sendError(403);
                    return;
                }
                try {
                    upsUserVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "upsUser", UpsUserVo.class);
                } catch (SOAParseException e) {
                    httpResponse.sendError(500);
                    return;
                }
                // 第一次登录系统
                // 保存用户信息到Session
                UserUtil.setUser2Session(upsUserVo);
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

}
