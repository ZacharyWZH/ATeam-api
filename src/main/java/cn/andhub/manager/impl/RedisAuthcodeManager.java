package cn.andhub.manager.impl;

import cn.andhub.config.Constants;
import cn.andhub.manager.AuthcodeManager;
import cn.andhub.model.AuthcodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by zachary on 2017/4/29.
 */
@Component
public class RedisAuthcodeManager implements AuthcodeManager {
    private RedisTemplate<String,String> redis;

    @Autowired
    public void setRedis(@Qualifier("redisTemplate") RedisTemplate redis){
        this.redis = redis;
    }

    /**
     * @param email 用户的email
     * @return
     */
    @Override
    public AuthcodeModel createAuthCode(String email) {
        //生成四位随机AuthCode,存储到redis并设置有效时间为5分钟
        String code = (int)(Math.random()*10000)/1+"";
        redis.boundValueOps(email).set(code, Constants.AUTHCODE_EXPIRES_MIN, TimeUnit.MINUTES);
        return new AuthcodeModel(email,code);
    }

    @Override
    public boolean checkAuthCode(AuthcodeModel model) {
        if (model == null) {
            return false;
        }
        String code = redis.boundValueOps(model.getEmail()).get();
        if (code == null || !code.equals(model.getCode())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，删除redis中的当前验证码
        redis.delete(model.getEmail());
        return true;
    }
}
