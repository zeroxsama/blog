package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 戴智钧
 * @Date 2019/12/21 20:55
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
