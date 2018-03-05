package com.ziroom.eunomia.dashboard.model;

/**
 * <p>Token的Model类，可以增加字段提高安全性，例如时间戳、url签名</p>
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
public class TokenModel {

    //用户id
    private String userId;

    //随机生成的uuid
    private String token;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
