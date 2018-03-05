package com.ziroom.eunomia.common.model;

import lombok.Getter;

/**
 * <p>自定义状态</p>
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
@Getter
public enum ResultStatus {
    SUCCESS(10000, "成功"),
    ERROE(-10000, "系统异常错误"),
    USERNAME_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
    USER_NOT_FOUND(10002, "用户不存在"),
    USER_NOT_LOGIN(10003, "用户未登录"),
    AUTHENTICATION_FAIL(10004, "Token失效或没有权限"),
    MISS_TOKEN(10005, "缺失Token"),
    MISS_PARAMETER(10006, "缺失参数"),
    ILLEGAL_PARAMETER(10007, "非法参数");

    private int code;

    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
