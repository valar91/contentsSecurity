package com.ziroom.eunomia.dashboard.interceptor;

import com.alibaba.fastjson.JSON;
import com.ziroom.eunomia.common.model.ResultModel;
import com.ziroom.eunomia.common.model.ResultStatus;
import com.ziroom.eunomia.dashboard.annotation.Authorization;
import com.ziroom.eunomia.dashboard.config.Constants;
import com.ziroom.eunomia.dashboard.manager.TokenManager;
import com.ziroom.eunomia.dashboard.model.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * <p>Api 接口权限拦截器</p>
 * <p>
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
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("redistokenManager")
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("============================权限拦截器启动==============================");
        request.setAttribute("starttime",System.currentTimeMillis());

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        //验证token
        TokenModel model = tokenManager.getToken(authorization);
        if (tokenManager.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            log.warn("UNAUTHORIZED , authorization : {}, request url :",authorization, request.getRequestURL());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(
                    JSON.toJSONString(
                            ResultModel.error(ResultStatus.AUTHENTICATION_FAIL)
                    )
            );
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("===========================权限拦截器执行处理完毕=============================");
        long starttime = (long) request.getAttribute("starttime");
        request.removeAttribute("starttime");
        long endtime = System.currentTimeMillis();
        log.debug("============请求地址："+request.getRequestURI()+"：处理时间：{}",(endtime-starttime)+"ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.addHeader("set-cookie", "23131312313123132123132");
        log.debug("============================权限拦截器关闭==============================");
    }
}
