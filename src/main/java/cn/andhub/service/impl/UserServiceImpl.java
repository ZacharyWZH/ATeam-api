package cn.andhub.service.impl;


import cn.andhub.domain.User;
import cn.andhub.domain.UserAuths;
import cn.andhub.mapper.UserAuthsMapper;
import cn.andhub.mapper.UserMapper;
import cn.andhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by zachary on 2017/5/7.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserAuthsMapper userAuthsMapper;

    /**
     * 登录时验证邮箱和密码
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean checkPassword(String email, String password) {
        if(!userAuthsMapper.findByEmail(email).getPassword().equals(password)){
            return false;
        }
        return true;
    }

    /**
     * 在UserAuth和User表中添加对应用户，并返回添加的User
     * @param username
     * @param email
     * @param password
     * @return
     */
    @Override
    public User addUser(String username, String email, String password) {
        userAuthsMapper.insert(email,password);
        UserAuths userAuths = userAuthsMapper.findByEmail(email);
        userMapper.insert(userAuths.getUser_id(),username);
        return userMapper.findByUserId(userAuths.getUser_id());
    }

    /**
     * 修改密码
     * @param email
     * @param newPasswrod
     */
    @Override
    public void modifyPassword(String email , String newPasswrod) {
        userAuthsMapper.update(email,newPasswrod);
    }

    /**
     * 检查email是否已被注册
     * @param email
     * @return
     */
    @Override
    public boolean emailHasExist(String email) {
        UserAuths userAuths = userAuthsMapper.findByEmail(email);
        if(userAuths!=null){
            return true;
        }
        return false;
    }

    @Override
    public UserAuths findUserAuthsByEmail(String email) {
        return userAuthsMapper.findByEmail(email);
    }

    /**
     * 根据email删除用户
     * @param email
     */
    @Override
    public void deleteUser(String email) {
        UserAuths userAuths = userAuthsMapper.findByEmail(email);
        userAuthsMapper.delete(email);
        userMapper.delete(userAuths.getUser_id());
    }

    @Override
    public void delete(User user) {
        userMapper.delete(user.getUser_id());
        userAuthsMapper.deleteById(user.getUser_id());
    }
}
