package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.Music;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 戴智钧
 * @Date 2019/12/26 11:11
 */
public interface MusicDao extends JpaRepository<Music,Long> {
}
