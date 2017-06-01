package cn.andhub.mapper;

import cn.andhub.domain.UserAuths;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * Created by zachary on 2017/5/5.
 */
@Repository
@Mapper
public interface UserAuthsMapper {

    @Select("select * from user_auths where email = #{email}")
    public UserAuths findByEmail(@Param("email") String email);

    @Select("select * from user_auths where user_id = #{user_id}")
    public UserAuths findById(@Param("user_id") long user_id);

    @Insert("insert into user_auths(email,password) values (#{email} , #{password}) ")
    public int insert(@Param("email") String email, @Param("password") String password);

    @Update("update user_auths set password = #{password} where email = #{email}")
    public int update(@Param("email") String email, @Param("password") String password);

    @Delete("delete from user_auths where email = #{email}")
    public int delete(@Param("email") String email);

    @Delete("delete from user_auths where user_id = #{user_id}")
    public void deleteById(@Param("user_id") long id);
}
