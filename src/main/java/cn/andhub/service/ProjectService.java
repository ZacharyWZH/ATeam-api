package cn.andhub.service;



import cn.andhub.domain.Project;
import cn.andhub.domain.User;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
public interface ProjectService {

    public void createProject(Project project, long team_id);

    public void deleteProject(long project_id);

    public User getCreater(long project_id);

    public void addProjectUser(long project_id, long user_id);

    public List<User> getProjectUsers(long project_id);

    public List<Project> getMyProject(long user_id);

    public List<Project> getMyCreatedProject(long user_id);

    public List<Project> getTeamProjects(long team_id);

    public void deleteByTeamId(long team_id);




}
