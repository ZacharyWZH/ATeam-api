package cn.andhub.service;



import cn.andhub.domain.Team;
import cn.andhub.domain.User;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
public interface TeamService {

    public void createTeam(String team_name, long creater_id);

    public void deleteTeam(long team_id);

    public void addTeamUser(long team_id, String email);

    public void joinTeam(long team_id, long user_id);

    public List<Team> findTeam(String search_str);

    public List<Team> getMyTeam(long user_id);

    public List<User> getTeamUsers(long team_id);

    public User getTeamCreater(long team_id);

    public List<Team> getMyCreatedTeam(long user_id);

    public Team getTeam(long team_id);

    public boolean userExitsInTeam(long team_id , long user_id);
}
