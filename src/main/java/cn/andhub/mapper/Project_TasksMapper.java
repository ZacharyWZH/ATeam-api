package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/25.
 */
@Mapper
@Repository
public interface Project_TasksMapper {

    @Insert("insert into project_tasks (project_id,task_id) values (#{project_id} , #{task_id})")
    public int insert(@Param("project_id") long project_id , @Param("task_id") long task_id);

    @Select("select task_id from project_tasks where project_id = #{project_id}")
    public List<Long> getTasks(@Param("project_id") long project_id);

    @Delete("delete from project_tasks where task_id = #{task_id}")
    public int deleteByTaskId(@Param("task_id") long task_id);
}
