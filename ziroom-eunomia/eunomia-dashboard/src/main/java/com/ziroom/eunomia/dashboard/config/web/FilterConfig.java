package com.ziroom.eunomia.dashboard.config.web;

import com.ziroom.eunomia.dashboard.filter.AutoSetUserAdapterFilter;
import com.ziroom.eunomia.dashboard.listener.ConfigInitializerListener;
import com.ziroom.minsu.services.common.entity.UpsConfig;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>CAS + UPS 认证、权限 过滤器</p>
 * <p> cas 配置项参考
 *     @see org.jasig.cas.client.authentication.AuthenticationFilter
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
@Configuration
public class FilterConfig {

    @Value("${spring.cas.cas-server-url-prefix}")
    private String casServerUrlPrefix;

    @Value("${spring.cas.cas-server-login-url}")
    private String casServerLoginUrl;

    @Value("${spring.cas.ignore-pattern}")
    private String ignorePattern;

    @Value("${spring.cas.server-name}")
    private String serverName;

    /**
     *
     * 初始化全局参数和静态资源版本号
     *
     * @author zhangyl2
     * @created 2017年11月08日 15:09
     * @param
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigInitializerListener> configInitializerListener() {
        ServletListenerRegistrationBean<ConfigInitializerListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(configInitializerListenerBean());
        listener.setOrder(1);
        return listener;
    }

    @Bean
    public ConfigInitializerListener configInitializerListenerBean(){
        return new ConfigInitializerListener();
    }

    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(2);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.addInitParameter("casServerUrlPrefix", casServerUrlPrefix);
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    /**
     * 该过滤器负责对Ticket的校验工作
     */
    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.addInitParameter("casServerUrlPrefix", casServerUrlPrefix);
        filterRegistration.addInitParameter("serverName", serverName);
        filterRegistration.setOrder(2);
        return filterRegistration;
    }

    /**
     * 该过滤器负责用户的认证工作
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AuthenticationFilter());
        filterRegistration.addUrlPatterns("/*");
        //casServerLoginUrl:cas服务的登陆url
        filterRegistration.addInitParameter("casServerLoginUrl", casServerLoginUrl);
        //本项目登录ip+port
        filterRegistration.addInitParameter("serverName", serverName);

        // 忽略指定URL
        if(ignorePattern != null && !"".equals(ignorePattern)){
            filterRegistration.addInitParameter("ignorePattern", ignorePattern);
        }

        filterRegistration.setOrder(3);
        return filterRegistration;
    }

    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     *
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new HttpServletRequestWrapperFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(4);
        return filterRegistration;
    }

    /**
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     比如AssertionHolder.getAssertion().getPrincipal().getName()。
     这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
     */
    @Bean
    public FilterRegistrationBean assertionThreadLocalFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AssertionThreadLocalFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(5);
        return filterRegistration;
    }

    /**
     * 
     * 自动根据单点登录的结果设置本系统的用户信息
     * 
     * @author zhangyl2
     * @created 2017年11月07日 14:24
     * @param 
     * @return 
     */
    @Bean
    public FilterRegistrationBean autoSetUserAdapterFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AutoSetUserAdapterFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(6);
        return filterRegistration;
    }

    /**
     *
     * UPS系统配置
     *
     * @author zhangyl2
     * @created 2017年11月07日 16:31
     * @param
     * @return
     */
    @Bean
    public UpsConfig upsConfig(@Value("${ups.systemCode}")String systemCode, @Value("${ups.userMsgApi}")String userMsgApi){
        UpsConfig upsConfig = new UpsConfig();
        upsConfig.setSystemCode(systemCode);
        upsConfig.setUserMsgApi(userMsgApi);
        return upsConfig;
    }
}
