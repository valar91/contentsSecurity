package com.ziroom.eunomia.dashboard.config.web;

import com.ziroom.eunomia.dashboard.interceptor.AuthInterceptor;
import com.ziroom.eunomia.dashboard.interceptor.AuthorizationInterceptor;
import com.ziroom.eunomia.dashboard.interceptor.InitGlobalParamsInterceptor;
import com.ziroom.eunomia.dashboard.resolvers.CurrentUserFidMethodArgumentResolver;
import com.ziroom.eunomia.dashboard.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * <p>自启动SpringMvc配置</p>
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
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index");
        registry.addViewController("sensitive/verification").setViewName("sensitiveWord/verification");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // api 拦截器
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/api/*");

        // 菜单拦截
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/index/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/logout/**")
                .excludePathPatterns("/noAuthority/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/sensitive/**");

        registry.addInterceptor(initGlobalParamsInterceptor())
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
        argumentResolvers.add(currentUserFidMethodArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public HandlerMethodArgumentResolver currentUserFidMethodArgumentResolver() {
        return new CurrentUserFidMethodArgumentResolver();
    }

    @Bean
    public HandlerInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Bean
    public HandlerInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public HandlerInterceptor initGlobalParamsInterceptor() {
        return new InitGlobalParamsInterceptor();
    }
}
