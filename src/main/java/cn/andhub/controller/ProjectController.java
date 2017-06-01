package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.Project;
import cn.andhub.domain.Team;
import cn.andhub.domain.User;
import cn.andhub.domain.UserAuths;
import cn.andhub.model.ResultModel;
import cn.andhub.service.ProjectService;
import cn.andhub.service.TeamService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/14.
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TeamService teamService;

    @RequestMapping(value = "/createpersonp" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "创建个人Team")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> createPresenProject(@CurrentUser UserAuths userAuths , @RequestParam String project_name , @RequestParam String project_detail){
        Project project = new Project();
        project.setCreater_id(userAuths.getUser_id());
        project.setProject_name(project_name);
        project.setProject_detail(project_detail);
        project.setProject_type(2);
        project.setCreate_time(new Timestamp(System.currentTimeMillis()));
        projectService.createProject(project,0);
        return new ResponseEntity<ResultModel>(ResultModel.ok("项目创建成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/createteamp" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "团队创建Team")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> deleteProject(@CurrentUser UserAuths userAuths , @RequestParam String project_name
            ,@RequestParam String project_detail ,@RequestParam long team_id ){
        Project project = new Project();
        project.setCreater_id(userAuths.getUser_id());
        project.setProject_name(project_name);
        project.setProject_detail(project_detail);
        project.setProject_type(1);
        project.setCreate_time(new Timestamp(System.currentTimeMillis()));
        projectService.createProject(project,team_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("项目创建成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/adduser" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "向Project添加User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> addProjectUser(@RequestParam long project_id , @RequestParam long user_id){
        projectService.addProjectUser(project_id,user_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("添加用户成功"), HttpStatus.OK);
    }

    // TODO: 2017/5/24
    @RequestMapping(value = "/getmyprojects" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取我加入的Project列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getMyProjects(@CurrentUser UserAuths userAuths){
        //先获取用户加入的团队
        //返回团队下的项目
        List<Team> team_list = teamService.getMyTeam(userAuths.getUser_id());
        List<Project> projects = new LinkedList<Project>();
        for(Team team:team_list){
            List<Project> temp = projectService.getTeamProjects(team.getTeam_id());
            for (Project p : temp){
                projects.add(p);
            }
        }
        //return new ResponseEntity<ResultModel>(ResultModel.ok(list),HttpStatus.OK);
        //List<Project> list = projectService.getMyProject(userAuths.getUser_id());
        return new ResponseEntity<ResultModel>(ResultModel.ok(projects), HttpStatus.OK);
    }


    @RequestMapping(value = "/getmycreatep" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取我创建的Project列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getMyCreateProjects(@CurrentUser UserAuths userAuths){
        List<Project> list = projectService.getMyCreatedProject(userAuths.getUser_id());
        return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/getprojectusers" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Project下的User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getProjectUsers(@RequestParam long project_id){
        List<User> list = projectService.getProjectUsers(project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/getteamprojects" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Team下的Project列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTeamProjects(@RequestParam long team_id){
        List<Project> list = projectService.getTeamProjects(team_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);

    }


    @RequestMapping(value = "/getprojectcreater" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Project创建者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getProjectCreater(@RequestParam long project_id){
        User user = projectService.getCreater(project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(user), HttpStatus.OK);
    }


    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "删除Project" , notes = "只有创建者能删除Project")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> deleteProject(@CurrentUser UserAuths userAuths,@RequestParam long project_id){
        if(projectService.getCreater(project_id).getUser_id()!=userAuths.getUser_id()){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PERMISSION_FALSE), HttpStatus.OK);
        }
        projectService.deleteProject(project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Project已删除"), HttpStatus.OK);
    }






}
