package cn.andhub.service;


import cn.andhub.domain.User;
import cn.andhub.domain.UserAuths;

/**
 * Created by zachary on 2017/5/5.
 */
public interface UserService {


    public boolean checkPassword(String email, String password);

    public User addUser(String username, String email, String password);

    public void modifyPassword(String email, String newPassword);

    public boolean emailHasExist(String email);

    public UserAuths findUserAuthsByEmail(String email);

    public void deleteUser(String email);

    public void delete(User user);

}
