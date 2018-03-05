package com.ziroom.eunomia.dashboard.manager;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.eunomia.dashboard.config.Constants;
import com.ziroom.eunomia.dashboard.model.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * <p>Token-Redis管理</p>
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
@Component("redistokenManager")
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<String, String> redis;

    @Autowired
    public void setRedis(@Qualifier(value = "stringRedisTemplate") RedisTemplate redis,
                         RedisConnectionFactory factory
                         ) {
        this.redis = redis;
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
        redis.setConnectionFactory(factory);
    }

    /**
     * 暂时用uuid作为token鉴权，待增加可扩展接口
     * @param userId 指定用户的id
     * @return
     */
    @Override
    public TokenModel createToken(String userId) {

        String token = UUIDGenerator.hexUUID();

        TokenModel model = new TokenModel(userId, token);

        //存储到redis并设置过期时间
        redis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
        return model;
    }

    /**
     * 这里涉及加密规则
     * 暂定：orgUsername_token
     * @param authentication 加密后的字符串
     * @return
     */
    @Override
    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split(Constants.ORG_TOKEN_TIE);
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = String.valueOf(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
        return true;
    }

    @Override
    public void deleteToken(String userId) {
        redis.delete(userId);
    }
}
