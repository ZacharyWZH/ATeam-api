package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.User;
import cn.andhub.domain.UserAuths;
import cn.andhub.manager.impl.RedisAuthcodeManager;
import cn.andhub.model.AuthcodeModel;
import cn.andhub.model.ResultModel;
import cn.andhub.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zachary on 2017/4/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisAuthcodeManager redisAuthcodeManager;

    /**
     * 注册新用户，发送验证码检查邮箱
     * @param email
     * @param username
     * @param password
     * @param authCode
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public ResponseEntity<ResultModel> createUser(@RequestParam String email , @RequestParam String username ,
                                                  @RequestParam String password , @RequestParam String authCode){
        AuthcodeModel authcodeModel = new AuthcodeModel(email,authCode);

        //邮箱已经被注册
        if(userService.findUserAuthsByEmail(email)!=null){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USER_ALREADY_EXISTING), HttpStatus.OK);
        }

        //验证码错误
        if(!redisAuthcodeManager.checkAuthCode(authcodeModel)){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.AUTHCODE_ERROR), HttpStatus.OK);
        }

        User user = userService.addUser(username,email,password);
        return new ResponseEntity<ResultModel>(ResultModel.ok(user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> modifyUsername(@CurrentUser User user){
        userService.delete(user);
        return new ResponseEntity<ResultModel>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 修改用户密码，不需要token鉴权
     * 通过邮件验证码验证
     * @param email
     * @param newPassword
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/modifypassword" , method = RequestMethod.POST )
    @ApiOperation(value = "修改密码" , notes = "通过邮件验证码验证权限，用户在登录和未登录状态下都有可调用")
    public ResponseEntity<ResultModel> modifyPassword(@RequestParam String email , @RequestParam String newPassword ,
                                                      @RequestParam String authCode){
        //先检查验证码
        AuthcodeModel authcodeModel = new AuthcodeModel(email,authCode);

        //检查新密码和旧密码是否相同
        if(userService.findUserAuthsByEmail(email).getPassword().equals(newPassword)){
            return  new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.OLDANDNEW_PASSWORD_REPEAT), HttpStatus.OK);
        }

        //验证码错误
        if(!redisAuthcodeManager.checkAuthCode(authcodeModel)) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.AUTHCODE_ERROR), HttpStatus.OK);
        }

        //修改数据库中的密码
        UserAuths userAuths = userService.findUserAuthsByEmail(email);
        userAuths.setPassword(newPassword);
        userService.modifyPassword(email,newPassword);
        return new ResponseEntity<ResultModel>(ResultModel.ok("修改密码成功"), HttpStatus.OK);
    }

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/modifyuserinfo")
    @ApiOperation(value = "修改用户资料(未实现)" , notes = "修改当前用户字段，需要登录")
    public void modifyUserInfo(@RequestBody User user){

    }



}
