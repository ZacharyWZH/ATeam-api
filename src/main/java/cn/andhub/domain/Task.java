package cn.andhub.domain;

import java.sql.Timestamp;

/**
 * Created by zachary on 2017/5/5.
 */
public class Task {

    private long task_id;
    private String task_name;
    private long creater_id;
    private String task_status;
    private String task_detail;
    private Timestamp start_time;
    private Timestamp end_time;

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public long getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(long create_id) {
        this.creater_id = create_id;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getTask_detail() {
        return task_detail;
    }

    public void setTask_detail(String task_detail) {
        this.task_detail = task_detail;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }
}
