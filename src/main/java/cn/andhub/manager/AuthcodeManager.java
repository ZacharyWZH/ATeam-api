package cn.andhub.manager;

import cn.andhub.model.AuthcodeModel;

/**
 * Created by zachary on 2017/4/29.
 */
public interface AuthcodeManager {
    /**
     * 创建AuthCode
     * @param email 用户的email
     * @return 生成的 AuthcodeModel
     */
    public AuthcodeModel createAuthCode(String email);

    /**
     * 检查AuthCode是否有效
     * @param model AuthCode
     * @return 是否有效
     */
    public boolean checkAuthCode(AuthcodeModel model);

}
