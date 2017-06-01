package cn.andhub.config;

/**
 * 常量
 * @author Zachary
 * @date 2017/4/26.
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * AuthCode有效期（分钟）
     */
    public static final int AUTHCODE_EXPIRES_MIN = 5;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

}
