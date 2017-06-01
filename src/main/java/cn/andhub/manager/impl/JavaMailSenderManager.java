package cn.andhub.manager.impl;

import cn.andhub.manager.MailManager;
import cn.andhub.model.AuthcodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by zachary on 2017/4/29.
 */
@Component
public class JavaMailSenderManager implements MailManager {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendAuthCodeMail(AuthcodeModel authcodeModel) {
        try
        {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            String nick = javax.mail.internet.MimeUtility.encodeText("AndHub Team");
            message.setFrom(new InternetAddress(nick + "<mail@andhub.cn>"));
            //message.setFrom("mail@andhub.cn");
            message.setTo(authcodeModel.getEmail());
            message.setSubject("AndHub注册验证码");
            message.setText("您的验证码是: "+authcodeModel.getCode());
            this.mailSender.send(mimeMessage);
            return ;
        }
        catch(Exception ex)
        {

            return;
        }
    }

    @Override
    public void sendMail(String email, String subject, String text) {
        try
        {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            String nick = javax.mail.internet.MimeUtility.encodeText("AndHub Team");
            message.setFrom(new InternetAddress(nick + "<mail@andhub.cn>"));
            //message.setFrom("mail@andhub.cn");
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            this.mailSender.send(mimeMessage);
            return ;
        }
        catch(Exception ex)
        {

            return;
        }
    }

}


