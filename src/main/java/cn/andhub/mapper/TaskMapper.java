package cn.andhub.mapper;

import cn.andhub.domain.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface TaskMapper {

    @Select("select * from task where task_id = #{task_id}")
    public Task findByTaskId(@Param("task_id") long task_id);

    @Select("select creater_id from task where task_id = #{task_id}")
    public Long getCreater(@Param("task_id") long task_id);

    @Insert("insert into task(task_name,creater_id,task_status,task_detail,start_time,end_time) " +
            "values(#{task_name} , #{creater_id} , #{task_status} , #{task_detail} , #{start_time} , #{end_time})")
    public int insert(Task task);

    @Update("update task set task_status = #{task_status} where task_id = #{task_id}")
    public int updateTaskStatus(@Param("task_id") long task_id, @Param("task_status") String task_status);

    @Delete("delete from task where task_id = #{task_id}")
    public int deleteByTaskId(@Param("task_id") long task_id);

    @Select("select max(task_id) from task")
    public long getMaxId();
}
