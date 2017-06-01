package cn.andhub.mapper;

import cn.andhub.domain.Team;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface TeamMapper {

    @Select("select * from team where team_id = #{team_id}")
    public Team findByTeamId(@Param("team_id") long team_id);

    @Select("select * from team where team_name like %#{search_str}%")
    public List<Team> findByTeamNameLike(@Param("search_str") String search_str);

    @Select("select creater_id from team where team_id = #{team_id}")
    public long findTeamCreater(@Param("team_id") long team_id);

    @Select("select team_id from team where creater_id = #{user_id}")
    public List<Long> findMyCreatedTeam(long user_id);

    @Insert("insert into team(team_name,creater_id) values (#{team_name} , #{creater_id})")
    public int insert(@Param("team_name") String team_name, @Param("creater_id") long creater_id);

    @Delete("delete from team where team_id = #{team_id}")
    public int delete(@Param("team_id") long team_id);

    @Select("select max(team_id) from team")
    public long getMaxId();
}
