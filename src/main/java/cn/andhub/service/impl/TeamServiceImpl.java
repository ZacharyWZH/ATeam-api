package cn.andhub.service.impl;

import cn.andhub.domain.Team;
import cn.andhub.domain.User;
import cn.andhub.mapper.*;
import cn.andhub.service.ProjectService;
import cn.andhub.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zachary on 2017/5/8.
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    Team_UsersMapper team_usersMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserAuthsMapper userAuthsMapper;

    @Autowired
    Team_ProjectsMapper team_projectsMapper;

    @Autowired
    ProjectService projectService;

    /**
     * 创建一个Team
     * @param team_name
     * @param creater_id
     * @return
     */
    @Override
    public void createTeam(String team_name, long creater_id) {
        teamMapper.insert(team_name,creater_id);
        team_usersMapper.insert(teamMapper.getMaxId(),creater_id);
    }

    /**
     * 删除Team
     * 只有创建者可以删除
     * 该Team的所有相关信息全都会被删除
     * @param team_id
     */
    @Override
    public void deleteTeam(long team_id) {
        projectService.deleteByTeamId(team_id);
        teamMapper.delete(team_id);
        team_usersMapper.deleteByTeamId(team_id);
    }

    /**
     * 创建者添加Team的成员，根据邮箱获得suer_id
     * @param team_id
     * @param email
     */
    @Override
    public void addTeamUser(long team_id, String email) {
        team_usersMapper.insert(team_id,userAuthsMapper.findByEmail(email).getUser_id());
    }

    /**
     * 用户加入一个Team
     * team_id通过findTeam()方法获得
     * @param team_id
     * @param user_id
     */
    @Override
    public void joinTeam(long team_id, long user_id) {
        team_usersMapper.insert(team_id,user_id);
    }

    /**
     * 关键字模糊查询Team
     * @param search_str
     * @return
     */
    @Override
    public List<Team> findTeam(String search_str) {
        return null;
    }

    /**
     * 用户获取自己所加入的Team
     * @param user_id
     * @return
     */
    @Override
    public List<Team> getMyTeam(long user_id) {
        List<Long> teamIdList = team_usersMapper.findTeamsByUserId(user_id);
        List<Team> list = new LinkedList<Team>();
        for (Long team_id:teamIdList) {
            Team team = teamMapper.findByTeamId(team_id);
            list.add(team);
        }
        return list;
    }

    /**
     * 获取Team中的用户
     * @param team_id
     * @return
     */
    @Override
    public List<User> getTeamUsers(long team_id) {
        List<Long> userIdList = team_usersMapper.findUsersByTeamId(team_id);
        List<User> list = new LinkedList<User>();
        for (Long user_id:userIdList) {
            User user = userMapper.findByUserId(user_id);
            list.add(user);
        }
        return list;
    }

    /**
     *获取Team的创建者
     * @return
     */
    @Override
    public User getTeamCreater(long team_id) {
        User user = userMapper.findByUserId(teamMapper.findTeamCreater(team_id));
        return user;
    }

    /**
     * 获取当前用户创建的团队
     * 显示团队列表区域分两步分，请求getMyTeam()接口和此接口
     * @param user_id
     * @return
     */
    @Override
    public List<Team> getMyCreatedTeam(long user_id) {
        List<Long> longs = teamMapper.findMyCreatedTeam(user_id);
        List<Team> list = new LinkedList<Team>();
        for (Long team_id:longs) {
            list.add(teamMapper.findByTeamId(team_id));
        }
        return list;
    }

    @Override
    public Team getTeam(long team_id) {
        return teamMapper.findByTeamId(team_id);
    }

    @Override
    public boolean userExitsInTeam(long team_id, long user_id) {
        if((team_usersMapper.isUserInTeam(team_id,user_id))!= null){
            return true;
        }
        return false;
    }
}
