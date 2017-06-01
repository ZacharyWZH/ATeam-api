package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface Team_ProjectsMapper {

    @Select("select project_id from team_projects where team_id = #{team_id}")
    public List<Long> findProjectsByTeamId(@Param("team_id") long team_id);

    @Insert("insert into team_projects(team_id,project_id) values(#{team_id},#{project_id})")
    public int insert(@Param("team_id") long team_id, @Param("project_id") long project_id);

    @Delete("delete from team_projects where project_id = #{project_id}")
    public int delete(@Param("project_id") long project_id);
}
