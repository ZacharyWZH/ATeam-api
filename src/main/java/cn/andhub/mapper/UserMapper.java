package cn.andhub.mapper;

import cn.andhub.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by zachary on 2017/5/5.
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    public User findByUsername(String username);

    @Select("select * from user where user_id = #{user_id}")
    public User findByUserId(@Param("user_id") long user_id);

    @Insert("insert into user(user_id,username) values (#{user_id} , #{username})")
    public int insert(@Param("user_id") long user_id, @Param("username") String username);

    @Update("update user set username = #{username} where user_id = #{user_id} ")
    public int updata(@Param("user_id") long user_id, @Param("username") String username);

    @Delete("delete from user where user_id = #{user_id}")
    public int delete(@Param("user_id") long user_id);
}
