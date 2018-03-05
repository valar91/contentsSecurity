package com.ziroom.eunomia.dashboard.resolvers;

import com.ziroom.eunomia.dashboard.annotation.CurrentUserFid;
import com.ziroom.eunomia.dashboard.config.Constants;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * <p>注解方法@CurrentUserFid参数注入</p>
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
public class CurrentUserFidMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(String.class) &&
                parameter.hasParameterAnnotation(CurrentUserFid.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String currentUserFId = (String) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserFId != null) {
            return currentUserFId;
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
