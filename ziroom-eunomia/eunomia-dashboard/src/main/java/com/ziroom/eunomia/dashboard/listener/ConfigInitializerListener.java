package com.ziroom.eunomia.dashboard.listener;


import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 初始化全局配置参数，说明：配置文件可以为多个，以逗号隔开,同时生成静态资源版本号
 * 
 * 版本号说明：
 * 1.版本号组成部分  包含两部分  A：可配置的变量X 
 * 2.X在未上生产环境前，每次发布项目，会重新赋值（统一配置），当上了生成环境，此值不在做修改
 * @author yd
 * @date 2016-03-24 中午
 * @version 1.0
 *
 */
public class ConfigInitializerListener implements ServletContextListener {
	
	/**
	 * 日志对象
	 */
	private static Logger log = LoggerFactory.getLogger(ConfigInitializerListener.class);
	
	/**
	 * ServletContext的上下文
	 */
	private ServletContext servletContext;
	
	/**
	 * js默认版本号
	 */
	public final static String JS_DEFAULT_VERSION="v_1.0";

    @Value("${JS_VERSION_X}")
    private String jsVersion;

	/**
	 * 加载配置文件，
	 */
	@Override
	public void contextInitialized(ServletContextEvent requestEvent) {
		
		
		this.setServletContext(requestEvent.getServletContext());
		
		//初始化版本号
		if(getServletContext()!=null){
			initResourceVersion();
		}
		
	}
	
	/**
	 * 初始化版本号
	 * @author yd
	 * @date 2016-03-25 18:42
	 * @version 1.0
	 */
	private void initResourceVersion(){

        String version = "?v=";

	    if(Check.NuNStr(jsVersion)){
	        version = version + JS_DEFAULT_VERSION;
            LogUtil.error(log, "获取js版本号异常，默认版本号version={}",ConfigInitializerListener.JS_DEFAULT_VERSION);
        }else{
            version = version + jsVersion;
            LogUtil.info(log, "当前静态资源文件的版本号VERSION={}", version);
        }

		getServletContext().setAttribute("VERSION", version);
	} 

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
