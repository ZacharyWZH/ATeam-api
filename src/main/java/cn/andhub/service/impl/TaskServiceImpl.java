package cn.andhub.service.impl;

import cn.andhub.domain.Task;
import cn.andhub.domain.User;
import cn.andhub.mapper.Project_TasksMapper;
import cn.andhub.mapper.TaskMapper;
import cn.andhub.mapper.Taskgroup_TasksMapper;
import cn.andhub.mapper.UserMapper;
import cn.andhub.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/8.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    Taskgroup_TasksMapper taskgroup_tasksMapper;

    @Autowired
    Project_TasksMapper project_tasksMapper;

    @Override
    public Task findById(long task_id) {
        return  taskMapper.findByTaskId(task_id);
    }

    /**
     * 创建Task
     * @param task
     * @param taskgroup_id
     */
    @Override
    public void createTask(Task task, long taskgroup_id) {
        taskMapper.insert(task);
        long task_id = taskMapper.getMaxId();
        taskgroup_tasksMapper.insert(taskgroup_id,task_id);
    }

    @Override
    public User getCeater(long task_id) {
        User user = userMapper.findByUserId(taskMapper.getCreater(task_id));
        return user;
    }

    /**
     * 删除Task
     * @param task_id
     */
    @Override
    public void deleteTask(long task_id) {
        taskMapper.deleteByTaskId(task_id);
        taskgroup_tasksMapper.deleteByTaskId(task_id);
    }

    /**
     * 更新该Task状态
     * @param task_id
     * @param newStatus
     */
    @Override
    public void updataTaskStatus(long task_id, String newStatus) {
        taskMapper.updateTaskStatus(task_id,newStatus);
    }

    /**
     * 获取Taskgroup下的Task列表
     * @param taskgroup_id
     * @return
     */
    @Override
    public List<Task> getTasks(long taskgroup_id) {
        List<Long> longs = taskgroup_tasksMapper.findTasksByTaskgroupId(taskgroup_id);
        List<Task> list = new LinkedList<Task>();
        for (Long task_id:longs) {
            list.add(taskMapper.findByTaskId(task_id));
        }
        return list;
    }

    @Override
    public void v2CreateTask(Task task, long project_id) {
        taskMapper.insert(task);
        long task_id = taskMapper.getMaxId();
        project_tasksMapper.insert(project_id,task_id);
    }

    @Override
    public List<Task> v2GetTasks(long project_id) {
        List<Long> longs = project_tasksMapper.getTasks(project_id);
        List<Task> list = new LinkedList<Task>();
        for (Long task_id:longs){
            list.add(taskMapper.findByTaskId(task_id));
        }
        return list;
    }
}
