package cn.andhub.manager;

import cn.andhub.model.AuthcodeModel;

/**
 * Created by zachary on 2017/4/29.
 */
public interface MailManager {

    /**
     * 发送验证码邮件
     * @param authcodeModel 验证码和邮件地址
     */
    public void sendAuthCodeMail(AuthcodeModel authcodeModel);

    public void sendMail(String email , String subject , String text );
}
