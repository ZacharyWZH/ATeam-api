package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.Team;
import cn.andhub.domain.User;
import cn.andhub.domain.UserAuths;
import cn.andhub.manager.MailManager;
import cn.andhub.model.ResultModel;
import cn.andhub.service.TeamService;
import cn.andhub.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zachary on 2017/5/14.
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    MailManager mailManager;

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "创建Team")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> createTeam(@CurrentUser UserAuths userAuths , @RequestParam String team_name){
        System.out.println("创建Team：user_id:"+userAuths.getUser_id()+",团队名:"+team_name);
        teamService.createTeam(team_name,userAuths.getUser_id());
        return new ResponseEntity<ResultModel>(ResultModel.ok("Team创建成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "删除Team" , notes = "只有创建者才能删除管理员，否则权限错误")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> deleteTeam(@CurrentUser UserAuths userAuths , @RequestParam long team_id ){
        //验证权限，当前用户不是管理员，返回权限错误
        if(teamService.getTeamCreater(team_id).getUser_id()!=userAuths.getUser_id()){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PERMISSION_FALSE),HttpStatus.OK);
        }
        //权限正确
        teamService.deleteTeam(team_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("删除Team成功"),HttpStatus.OK);
    }

    @RequestMapping(value = "/inviteuser" , method = RequestMethod.POST)
    @ApiOperation(value = "通过邮箱地址邀请用户到Team" , notes = "")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> inviteUserByMail(@RequestParam String email , @RequestParam long team_id){
        String team_name = teamService.getTeam(team_id).getTeam_name();
        //该邮箱未注册，发送邀请注册邮件
        if(!userService.emailHasExist(email)){
            mailManager.sendMail(email,"来自"+team_name+"团队的邀请",team_name+"邀请您的加入，注册地址"+"http://www.baidu.com");
            return new ResponseEntity<ResultModel>(ResultModel.ok("该邮箱未注册，已发送邀请邮件"),HttpStatus.OK);
        }
        UserAuths userAuths = userService.findUserAuthsByEmail(email);
        //User已经在团队中
        if(teamService.userExitsInTeam(team_id,userAuths.getUser_id())){
            return new ResponseEntity<ResultModel>(ResultModel.ok("用户已经在团队中"),HttpStatus.OK);
        }
        //邮箱已经注册，添加用户到团队并发送邮件通知
        teamService.addTeamUser(team_id,email);
        mailManager.sendMail(email,"来自"+team_name+"团队的邀请","您已被添加到"+team_name+"团队中，请及时确认");
        return new ResponseEntity<ResultModel>(ResultModel.ok("成员添加成功"),HttpStatus.OK);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.POST)
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    @ApiOperation(value = "关键字查询Team（未实现）" , notes = "输入关键字，迷糊查询Team")
    public void queryTeam(@RequestParam String str){
    }

    @RequestMapping(value = "/join" , method = RequestMethod.POST)
    @ApiOperation(value = "加入Team" , notes = "用户查询到Team后加入Team")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> joinTeam(@CurrentUser UserAuths userAuths , @RequestParam long team_id){
        teamService.joinTeam(team_id,userAuths.getUser_id());
        return new ResponseEntity<ResultModel>(ResultModel.ok("已加入Team"),HttpStatus.OK);
    }

    @RequestMapping(value = "/getmyteam" , method = RequestMethod.POST)
    @ApiOperation(value = "获取已经加入的Team" , notes = "用户获取已加入的Team")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getMyTeam(@CurrentUser UserAuths userAuths){
        List<Team> list = teamService.getMyTeam(userAuths.getUser_id());
        return new ResponseEntity<ResultModel>(ResultModel.ok(list),HttpStatus.OK);
    }

    @RequestMapping(value = "/getteamusers" , method = RequestMethod.POST)
    @ApiOperation(value = "获取Team中的User列表" , notes = "用户输入team_id获取Team中的User列表")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTeamUsers(@RequestParam long team_id){
        List<User> list = teamService.getTeamUsers(team_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(list),HttpStatus.OK);
    }

    @RequestMapping(value = "/getteamcreater" , method = RequestMethod.POST)
    @ApiOperation(value = "获取Team的创建者" , notes = "用户输入team_id获取Team的创建者")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTeamCreater(@RequestParam long team_id){
        User user = teamService.getTeamCreater(team_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(user),HttpStatus.OK);
    }




}
