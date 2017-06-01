package cn.andhub.domain;

/**
 * Created by zachary on 2017/5/25.
 */
public class Project_Tasks {
    private long id;
    private long project_id;
    private long task_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }
}
