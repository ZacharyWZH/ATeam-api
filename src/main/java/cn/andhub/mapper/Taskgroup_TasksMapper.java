package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface Taskgroup_TasksMapper {

    @Select("select task_id from taskgroup_tasks where taskgroup_id = #{taskgroup_id}")
    public List<Long> findTasksByTaskgroupId(@Param("taskgroup_id") long taskgroup_id);

    @Insert("insert into taskgroup_tasks(task_id,taskgroup_id) values(#{task_id} , #{taskgroup_id})")
    public int insert(@Param("taskgroup_id") long taskgroup_id, @Param("task_id") long task_id);

    @Delete("delete from taskgroup_tasks where task_id = #{task_id}")
    public int deleteByTaskId(@Param("task_id") long task_id);

    @Delete("delete from taskgroup_tasks where taskgroup_id = #{taskgroup_id}")
    public int deleteByTaskgroupId(@Param("taskgroup_id") long taskgroup_id);
}
