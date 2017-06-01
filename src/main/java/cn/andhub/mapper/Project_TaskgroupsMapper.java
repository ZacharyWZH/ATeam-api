package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface Project_TaskgroupsMapper {


    @Select("select taskgroup_id from project_taskgroups where project_id = #{project_id} ")
    public List<Long> findTaskgroupsByProjectId(@Param("project_id") long project_id);


    @Insert("insert into project_taskgroups(project_id,taskgroup_id) values(#{project_id} , #{taskgroup_id})")
    public int insert(@Param("project_id") long project_id, @Param("taskgroup_id") long tasgroup_id);

    @Delete("delete from project_taskgroups where taskgroup_id = #{taskgroup_id}")
    public int deleteByTaskgroupId(@Param("taskgroup_id") long taskgroup_id);

    @Delete("delete from project_taskgroups where project_id = #{project_id}")
    public int deleteByProject_id(@Param("project_id") long project_id);
}
