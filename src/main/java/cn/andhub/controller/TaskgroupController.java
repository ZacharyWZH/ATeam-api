package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.Taskgroup;
import cn.andhub.domain.UserAuths;
import cn.andhub.model.ResultModel;
import cn.andhub.service.TaskService;
import cn.andhub.service.TaskgroupService;
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

import java.util.List;

/**
 * Created by zachary on 2017/5/14.
 */
@RestController
@RequestMapping("/taskgroup")
public class TaskgroupController {

    @Autowired
    TaskgroupService taskgroupService;

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "创建Taskgroup")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> createTaskgroup(@CurrentUser UserAuths userAuths , @RequestParam String taskgroup_name,@RequestParam long project_id){
        taskgroupService.createTaskGroup(taskgroup_name,userAuths.getUser_id(),project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Taskgroup创建成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "删除Taskgroup" , notes = "只有创建者能删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> deleteTaskgroup(@CurrentUser UserAuths userAuths,@RequestParam long taskgroup_id){
        if(userAuths.getUser_id()!=taskgroupService.getCreater(taskgroup_id).getUser_id()){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PERMISSION_FALSE), HttpStatus.OK);
        }
        taskgroupService.deleteTaskGroup(taskgroup_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Taskgroup删除成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/get" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Project下的Taskgroup列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTaskgroups(@RequestParam long project_id){
        List<Taskgroup> list = taskgroupService.getTaskGroups(project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);
    }

}
