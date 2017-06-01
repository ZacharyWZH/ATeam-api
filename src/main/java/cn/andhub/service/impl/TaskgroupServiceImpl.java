package cn.andhub.service.impl;

import cn.andhub.domain.Task;
import cn.andhub.domain.Taskgroup;
import cn.andhub.domain.User;
import cn.andhub.mapper.Project_TaskgroupsMapper;
import cn.andhub.mapper.TaskgroupMapper;
import cn.andhub.mapper.Taskgroup_TasksMapper;
import cn.andhub.mapper.UserMapper;
import cn.andhub.service.TaskService;
import cn.andhub.service.TaskgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/8.
 */
@Service
public class TaskgroupServiceImpl implements TaskgroupService {

    @Autowired
    TaskgroupMapper taskgroupMapper;

    @Autowired
    Project_TaskgroupsMapper project_taskgroupsMapper;

    @Autowired
    Taskgroup_TasksMapper taskgroup_tasksMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    UserMapper userMapper;


    /**
     * 创建Taskgroup
     * @param taskgroupName
     * @param creater_id
     * @param project_id
     */
    @Override
    public void createTaskGroup(String taskgroupName, long creater_id,long project_id) {
        taskgroupMapper.insert(taskgroupName,creater_id);
        long taskgroup_id = taskgroupMapper.getMaxId();
        project_taskgroupsMapper.insert(project_id,taskgroup_id);
    }

    @Override
    public User getCreater(long taskgroup_id) {
        User user = userMapper.findByUserId(taskgroupMapper.getCreater(taskgroup_id));
        return user;
    }

    /**
     * 删除Taskgroup,同时该Taskgroup下的所有信息都会被删除
     * @param taskgroup_id
     */
    @Override
    public void deleteTaskGroup(long taskgroup_id) {
        List<Task> list = taskService.getTasks(taskgroup_id);
        for (Task task :list) {
            taskService.deleteTask(task.getTask_id());
        }
        taskgroupMapper.delete(taskgroup_id);
        project_taskgroupsMapper.deleteByTaskgroupId(taskgroup_id);
        taskgroup_tasksMapper.deleteByTaskgroupId(taskgroup_id);

    }

    /**
     * 通过ProjectId删除Taskgroup
     * @param project_id
     */
    @Override
    public void deleteByProjectId(long project_id) {
        List<Taskgroup> list = getTaskGroups(project_id);
        for (Taskgroup taskgroup:list) {
            deleteTaskGroup(taskgroup.getTaskgroup_id());
        }

    }

    /**
     * 获取指定Project下的Taskgroup列表
     * @param project_id
     * @return
     */
    @Override
    public List<Taskgroup> getTaskGroups(long project_id) {
        List<Long> longList = project_taskgroupsMapper.findTaskgroupsByProjectId(project_id);
        List<Taskgroup> list = new LinkedList<Taskgroup>();
        for (Long taskgroup_id:longList) {
            list.add(taskgroupMapper.findById(taskgroup_id));
        }
        return list;
    }
}
