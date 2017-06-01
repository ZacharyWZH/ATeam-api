package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Repository
@Mapper
public interface Team_UsersMapper {


    @Select("select team_id from team_users where user_id = #{user_id}")
    public List<Long> findTeamsByUserId(@Param("user_id") long user_id);

    @Select("select user_id from team_users where team_id = #{team_id}")
    public List<Long> findUsersByTeamId(@Param("team_id") long team_id);

    @Select("select id from  team_users where team_id = #{team_id} and user_id = #{user_id}")
    public Long isUserInTeam(@Param("team_id") long team_id , @Param("user_id") long user_id);

    @Insert("insert into team_users(team_id,user_id) values (#{team_id} , #{user_id})")
    public int insert(@Param("team_id") long team_id, @Param("user_id") long user_id);

    @Delete("delete from team_users where team_id = #{team_id}")
    public int deleteByTeamId(@Param("team_id") long team_id);
}
