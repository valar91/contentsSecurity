package com.ziroom.eunomia.dashboard.config;

/**
 * <p>常量库</p>
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
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 60;

    /**
     * token有效期（天）
     */
    public static final int TOKEN_EXPIRES_DAY = 30;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

    /**
     * OrgId - Access_token 组合连接符号
     */
    public static final String ORG_TOKEN_TIE = "#";

    /**
     * 当前登录的用户名
     */
    public static final String CURRENT_LOGIN_USER_NAME = "__CURRENT_LOGIN_USER_NAME__";

    /**
     * _const_cas_assertion_是CAS中存放登录用户名的session标志
     */
    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";

}
