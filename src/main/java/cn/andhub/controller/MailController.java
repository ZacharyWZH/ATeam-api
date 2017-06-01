package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.manager.impl.JavaMailSenderManager;
import cn.andhub.manager.impl.RedisAuthcodeManager;
import cn.andhub.model.AuthcodeModel;
import cn.andhub.model.ResultModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zachary on 2017/4/29.
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private RedisAuthcodeManager redisAuthcodeManager;

    @Autowired
    private JavaMailSenderManager javaMailSenderManager;

    @RequestMapping(value = "/verificationcode" , method = RequestMethod.GET)
    @ApiOperation(value = "获取注册验证码邮件")
    public ResponseEntity<ResultModel> sendAuthCodeEmail(@RequestParam String email)
    {
        AuthcodeModel authcodeModel = redisAuthcodeManager.createAuthCode(email);
        javaMailSenderManager.sendAuthCodeMail(authcodeModel);
        return new ResponseEntity<ResultModel>(ResultModel.ok("发送验证码成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/newtasknoti")
    @Authorization
    @ApiOperation(value = "新Task通知" , notes = "新Task被创建时，该Project中所有成员会收到任务通知")
    public void sendNewTaskNotification(){

    }
}
