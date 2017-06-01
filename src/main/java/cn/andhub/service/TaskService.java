package cn.andhub.service;



import cn.andhub.domain.Task;
import cn.andhub.domain.User;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
public interface TaskService {

    public Task findById(long task_id);

    public void createTask(Task task, long taskgroup_id);

    public User getCeater(long task_id);

    public void deleteTask(long task_id);

    public void updataTaskStatus(long task_id, String newStatus);

    public List<Task> getTasks(long taskgroup_id);

    // TODO: 2017/5/25

    public void v2CreateTask(Task task , long project_id);

    public List<Task> v2GetTasks(long project_id);

}
