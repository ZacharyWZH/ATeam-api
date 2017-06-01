package cn.andhub.config;

/**
 * 自定义请求状态码
 * @author Zachary
 * @date 2017/4/26.
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
    AUTHCODE_ERROR(-1004, "验证码错误"),
    OLDANDNEW_PASSWORD_REPEAT(-1005, "新密码与旧密码相同"),
    USER_ALREADY_EXISTING(-1006,"该邮箱已被注册"),
    PERMISSION_FALSE(-1007,"权限错误");





    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
