package cn.andhub.service.impl;

import cn.andhub.domain.Project;
import cn.andhub.domain.User;
import cn.andhub.mapper.ProjectMapper;
import cn.andhub.mapper.Project_UsersMapper;
import cn.andhub.mapper.Team_ProjectsMapper;
import cn.andhub.mapper.UserMapper;
import cn.andhub.service.ProjectService;
import cn.andhub.service.TaskgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/8.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    Project_UsersMapper project_usersMapper;

    @Autowired
    Team_ProjectsMapper team_projectsMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TaskgroupService taskgroupService;


    /**
     * 创建一个project,同时设定Team
     * @param project
     */
    @Override
    public void createProject(Project project , long team_id) {
        projectMapper.insert(project);
        long project_id = projectMapper.getMaxId();
        team_projectsMapper.insert(team_id,project_id);
    }

    /**
     *删除project，同时调用taskgroupService删除当前project下的其他内容
     * @param project_id
     */
    @Override
    public void deleteProject(long project_id) {
        projectMapper.delete(project_id);
        project_usersMapper.deleteByProjectId(project_id);
        team_projectsMapper.delete(project_id);
        taskgroupService.deleteByProjectId(project_id);

    }

    /**
     * 获取创建者
     * @param project_id
     * @return
     */
    @Override
    public User getCreater(long project_id) {
        return userMapper.findByUserId(projectMapper.getProjectCreater(project_id));
    }

    @Override
    public void addProjectUser(long project_id, long user_id) {
        project_usersMapper.insert(project_id,user_id);
    }

    /**
     * 获取Project的用户列表
     * @param project_id
     * @return
     */
    @Override
    public List<User> getProjectUsers(long project_id) {
        List<Long> longs = project_usersMapper.findUsersByProjectId(project_id);
        List<User> list = new LinkedList<User>();
        for (Long user_id:longs) {
            list.add(userMapper.findByUserId(user_id));
        }
        return list;
    }

    /**
     *获取当前用户加入的Project
     * 不包含创建的Project
     * @param user_id
     * @return
     */
    @Override
    public List<Project> getMyProject(long user_id) {
        List<Long> longs = project_usersMapper.findProjectsByUserId(user_id);
        List<Project> projects = new LinkedList<Project>();
        for (Long project_id:longs) {
            projects.add(projectMapper.findById(project_id));
        }
        return projects;
    }

    /**
     * 获取当前用户创建的Project
     * @param user_id
     * @return
     */
    @Override
    public List<Project> getMyCreatedProject(long user_id) {
        List<Long> longs = projectMapper.getMyCreatedProject(user_id);
        List<Project> list = new LinkedList<Project>();
        for (Long project_id:longs) {
            list.add(projectMapper.findById(project_id));
        }
        return list;
    }

    @Override
    public List<Project> getTeamProjects(long team_id) {
        List<Long> longs = team_projectsMapper.findProjectsByTeamId(team_id);
        List<Project> list = new LinkedList<Project>();
        for (Long project_id:longs) {
            list.add(projectMapper.findById(project_id));
        }
        return list;
    }

    @Override
    public void deleteByTeamId(long team_id) {
        List<Project> list = getTeamProjects(team_id);
        for (Project project :list) {
            deleteProject(project.getProject_id());
        }
    }
}
