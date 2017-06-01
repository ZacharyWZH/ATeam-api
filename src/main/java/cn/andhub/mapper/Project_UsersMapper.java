package cn.andhub.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zachary on 2017/5/5.
 */
@Repository
@Mapper
public interface Project_UsersMapper {


    @Select("select user_id from project_users where project_id = #{project_id}")
    public List<Long> findUsersByProjectId(@Param("project_id") long project_id);

    @Select("select project_id from project_users where user_id = #{user_id}")
    public List<Long> findProjectsByUserId(@Param("user_id") long user_id);

    @Insert("insert into project_users(project_id,user_id) values(#{project_id} , #{user_id})")
    public int insert(@Param("project_id") long project_id, @Param("user_id") long user_id);

    @Delete("delete from project_users where project_id = #{project_id}")
    public int deleteByProjectId(@Param("project_id") long project_id);

}
