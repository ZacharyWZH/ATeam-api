package cn.andhub.controller;

import cn.andhub.authorization.annotation.Authorization;
import cn.andhub.authorization.annotation.CurrentUser;
import cn.andhub.config.ResultStatus;
import cn.andhub.domain.*;
import cn.andhub.model.ResultModel;
import cn.andhub.service.ProjectService;
import cn.andhub.service.TaskService;
import cn.andhub.service.TaskgroupService;
import cn.andhub.service.TeamService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/14.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    TeamService teamService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskgroupService taskgroupService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "创建Task")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> createTask(@CurrentUser UserAuths userAuths , @RequestParam String task_name
            , @RequestParam String task_detail , @RequestParam String task_status , @RequestParam long start_time
            , @RequestParam long end_time , @RequestParam long taskgroup_id){
        Task task = new Task();
        task.setCreater_id(userAuths.getUser_id());
        task.setTask_name(task_name);
        task.setTask_detail(task_detail);
        task.setTask_status(task_status);
        task.setStart_time(new Timestamp(start_time));
        task.setEnd_time(new Timestamp(end_time));
        taskService.createTask(task,taskgroup_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Task创建成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "删除Task" , notes = "只有创建者可以删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> deleteTask(@CurrentUser UserAuths userAuths , @RequestParam long task_id){
        if(userAuths.getUser_id()!=taskService.findById(task_id).getCreater_id()){
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PERMISSION_FALSE), HttpStatus.OK);
        }
        taskService.deleteTask(task_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Task删除成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/getdetail" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Task详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTaksDetail(@RequestParam long task_id){
        Task task = taskService.findById(task_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(task), HttpStatus.OK);
    }

    @RequestMapping(value = "/gettasks" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "获取Taskgroup下的Taks列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTaskList(@RequestParam long taskgroup_id){
        List<Task> list = taskService.getTasks(taskgroup_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/updata" , method = RequestMethod.POST)
    @Authorization
    @ApiOperation(value = "修改Task信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> modifyTaskInfo(@RequestParam long task_id , @RequestParam String new_status){
        taskService.updataTaskStatus(task_id,new_status);
        return new ResponseEntity<ResultModel>(ResultModel.ok("Task状态修改成功"), HttpStatus.OK);
    }

    @RequestMapping(value = "/gettasklist" , method = RequestMethod.POST)
    @ApiOperation(value = "获取用户的Task")
    @Authorization@ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> getTaskList(@CurrentUser UserAuths userAuths){
        List<Team> team_list = teamService.getMyTeam(userAuths.getUser_id());
        List<Project> projects = new LinkedList<Project>();
        List<Task> tasks = new LinkedList<Task>();
        List<Taskgroup> taskgroups = new LinkedList<Taskgroup>();
        for(Team team:team_list){
            List<Project> temp = projectService.getTeamProjects(team.getTeam_id());
            for (Project p : temp){
                projects.add(p);
            }
        }
        for (Project project:projects){
            List<Taskgroup> temp = taskgroupService.getTaskGroups(project.getProject_id());
            for(Taskgroup taskgroup:temp){
                taskgroups.add(taskgroup);
            }
        }
        for (Taskgroup taskgroup:taskgroups){
            List<Task> tasks1 = taskService.getTasks(taskgroup.getTaskgroup_id());
            for (Task task:tasks1
                 ) {
                tasks.add(task);
            }
        }
        return new ResponseEntity<ResultModel>(ResultModel.ok(tasks),HttpStatus.OK);

    }

    // TODO: 2017/5/25
    @RequestMapping(value = "/v2/getalltask" , method = RequestMethod.POST)
    @ApiOperation(value = "v2:获取当前用户所有Task")
    @Authorization@ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> v2GetAllTask(@CurrentUser UserAuths userAuths){
        List<Team> team_list = teamService.getMyTeam(userAuths.getUser_id());
        List<Project> projects = new LinkedList<Project>();
        List<Task> tasks = new LinkedList<Task>();
        for(Team team:team_list){
            List<Project> temp = projectService.getTeamProjects(team.getTeam_id());
            for (Project p : temp){
                projects.add(p);
            }
        }
        for(Project p : projects){
            List<Task> temp = taskService.v2GetTasks(p.getProject_id());
            for(Task task : temp){
                tasks.add(task);
            }
        }
        return new ResponseEntity<ResultModel>(ResultModel.ok(tasks),HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/getaptasks" , method = RequestMethod.POST)
    @ApiOperation(value = "v2:获取当前项目下Task")
    @Authorization@ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> v2GetAProjectTasks(@CurrentUser UserAuths userAuths, @RequestParam long project_id){
        List<Task> tasks = taskService.v2GetTasks(project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok(tasks),HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/createtask" , method = RequestMethod.POST)
    @ApiOperation(value = "v2:创建Task")
    @Authorization@ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> v2GetAProjectTasks(@CurrentUser UserAuths userAuths , @RequestBody Task task , @RequestParam long project_id){
        Task newtask = new Task();
        newtask.setCreater_id(userAuths.getUser_id());
        newtask.setTask_name(task.getTask_name());
        newtask.setTask_detail(task.getTask_detail());
        newtask.setTask_status(task.getTask_status());
        //task.setStart_time(new Timestamp(start_time));
        //task.setEnd_time(new Timestamp(end_time));
        taskService.v2CreateTask(newtask,project_id);
        return new ResponseEntity<ResultModel>(ResultModel.ok("任务创建成功"),HttpStatus.OK);
    }

}
