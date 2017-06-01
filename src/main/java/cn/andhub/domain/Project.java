package cn.andhub.domain;

import java.sql.Timestamp;

/**
 * Created by zachary on 2017/5/5.
 */
public class Project {

    private long project_id;
    private long creater_id;
    private String project_name;
    private String project_detail;
    private Timestamp create_time;
    private int project_type;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(long creater_id) {
        this.creater_id = creater_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_detail() {
        return project_detail;
    }

    public void setProject_detail(String project_detail) {
        this.project_detail = project_detail;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getProject_type() {
        return project_type;
    }

    public void setProject_type(int project_type) {
        this.project_type = project_type;
    }
}
