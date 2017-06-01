package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.authorization.manager.TokenManager;
import cn.andhub.authorization.model.TokenModel;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.UserAuths;
import cn.andhub.model.ResultModel;
import cn.andhub.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * @author Zachary
 * @date 2017/4/26.
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "登录" , notes = "登录接口，获取token")
    public ResponseEntity<ResultModel> login(
            @ApiParam(required=true, name="email", value="邮箱") @RequestParam(required=true) String email,
            @ApiParam(required=true, name="password", value="密码") @RequestParam(required=true)  String password) {
        Assert.notNull(email, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        UserAuths userAuths = userService.findUserAuthsByEmail(email);
        if (userAuths == null ||  //未注册
                !userAuths.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.OK);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(userAuths.getUser_id());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录" , notes = "注销token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser UserAuths userAuths) {
        tokenManager.deleteToken(userAuths.getUser_id());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
