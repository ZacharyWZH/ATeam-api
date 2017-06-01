package cn.andhub.mapper;

import cn.andhub.domain.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface ProjectMapper {

    @Select("select * from project where project_id = #{project_id}")
    public Project findById(@Param("project_id") long project_id);

    @Insert("insert into project(creater_id,project_name,project_detail,create_time,project_type) " +
            "values(#{creater_id} , #{project_name} , #{project_detail} , #{create_time} , #{project_type})")
    public int insert(Project project);

    @Select("select creater_id from project where project_id = #{project_id}")
    public long getProjectCreater(long project_id);

    @Select("select project_id from project where creater_id = #{user_id}")
    public List<Long> getMyCreatedProject(long user_id);

    @Select("select max(project_id) from project")
    public long getMaxId();

    @Delete("delete from project where project_id = #{project_id}")
    public int delete(@Param("project_id") long project_id);
}
