package cn.cjun.blog.service;

import cn.cjun.blog.dao.UserDao;
import cn.cjun.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author 戴智钧
 * @Date 2019/12/21 20:53
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }


}
