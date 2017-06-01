package cn.andhub.service;



import cn.andhub.domain.Taskgroup;
import cn.andhub.domain.User;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
public interface TaskgroupService {

    public void createTaskGroup(String taskgroupName, long creater_id, long project_id);

    public User getCreater(long taskgroup_id);

    public void deleteTaskGroup(long taskgroup_id);

    public void deleteByProjectId(long project_id);

    public List<Taskgroup> getTaskGroups(long project_id);

}
