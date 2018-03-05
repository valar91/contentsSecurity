package com.ziroom.eunomia.dashboard.config.util;

import javax.servlet.http.HttpSession;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

import com.ziroom.eunomia.dashboard.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;

import java.util.Set;


/**
 * 
 * <p>用户工具类</p>
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
public class UserUtil {

	// 登陆人信息session标记
	private static final String LOGIN_USER_SESSION_ID = Constants.CURRENT_LOGIN_USER_NAME;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserUtil.class);
	
	private UserUtil(){
		
	}

	/**
	 * 
	 * 获取当前登录用户ups信息
	 *
	 * @author busj
	 * @created 2016-3-18 下午10:01:43
	 *
	 * @return
	 */
	public static UpsUserVo getUpsUserMsg() {
		UpsUserVo user = null ;
		try {
			HttpSession session = HttpUtil.getSession();
			
			Object object = session.getAttribute(LOGIN_USER_SESSION_ID);
			
			if(!Check.NuNObj(object)){
				user = (UpsUserVo)object;
			}
		} catch (Exception e) {
            LogUtil.error(LOGGER, "Failed to get current user from the session ", e) ;
		}
		return user ;
	}
	
	/**
	 * 
	 * 往sesson中注入用户
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:50:28
	 *
	 * @param user
	 */
	public static void setUser2Session(UpsUserVo user) {
		setUser2Session(user, HttpUtil.getSession());
    }

	/**
	 * 
	 * 往sesson中注入用户
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:50:28
	 *
	 * @param user
	 * @param session
	 */
	public static void setUser2Session(UpsUserVo user, HttpSession session) {
		session.setAttribute(LOGIN_USER_SESSION_ID, user);
    }

    /**
     * 
     * 判断资源权限
     * 
     * @author zhangyl2
     * @created 2017年11月15日 14:30
     * @param 
     * @return 
     */
    public static boolean checkResourceAuth(String res){
        UpsUserVo user = getUpsUserMsg();
	    if(!Check.NuNObj(user)){
	        Set<String> resourceVoSet = user.getResourceVoSet();
	        if(!Check.NuNCollection(resourceVoSet)){
	            return resourceVoSet.contains(res);
            }
        }
        return false;
    }

}
