package cn.andhub.mapper;

import cn.andhub.domain.Taskgroup;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * Created by zachary on 2017/5/5.
 */
@Repository
@Mapper
public interface TaskgroupMapper {

    @Select("select * from taskgroup where taskgroup_id = #{taskgroup_id}")
    public Taskgroup findById(@Param("taskgroup_id") long taskgroup_id);

    @Insert("insert into taskgroup(taskgroup_name,creater_id) values(#{taskgroup_name},#{creater_id})")
    public int insert(@Param("taskgroup_name") String taskgroup_name, @Param("creater_id") long creater_id);

    @Select("select creater_id from taskgroup where taskgroup_id = #{taskgroup_id}")
    public Long getCreater(@Param("taskgroup_id") long taskgroup_id);

    @Select("select max(taskgroup_id) from taskgroup")
    public long getMaxId();

    @Delete("delete from taskgroup where taskgroup_id = #{taskgroup_id}")
    public int delete(@Param("taskgroup_id") long taskgroup_id);

}
