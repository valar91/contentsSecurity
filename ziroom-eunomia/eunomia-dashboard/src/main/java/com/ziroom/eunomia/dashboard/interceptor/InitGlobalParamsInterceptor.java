package com.ziroom.eunomia.dashboard.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始化全局参数(具体其他全局参数都可以在这里配置)
 *
 * @author yd
 * @version 1.0
 * @date 2016-03-25
 */
public class InitGlobalParamsInterceptor extends HandlerInterceptorAdapter {

    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(InitGlobalParamsInterceptor.class);
    /**
     * 静态资源地址
     */
    @Value("${eunomia.static.resource.url}")
    private String staticResourceUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String serverHost = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String serverContext = request.getContextPath().equalsIgnoreCase("/") ? "" : request.getContextPath();
        // 增加服务器路径
        request.setAttribute("SERVER_HOST", serverHost);
        // 上下文
        request.setAttribute("SERVER_CONTEXT", serverContext);

        String basePath = null;
        if (serverHost != null && serverContext != null) {
            basePath = serverHost + serverContext + "/";
        }
        request.setAttribute("basePath", basePath);
        //CDN的静态地址
        request.setAttribute("staticResourceUrl", staticResourceUrl);
        return super.preHandle(request, response, handler);
    }

}
