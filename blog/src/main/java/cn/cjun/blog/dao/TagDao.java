package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 17:27
 */
public interface TagDao extends JpaRepository<Tag,Long>{
    Tag findByName(String name);

    @Query(value = "SELECT t.*,COUNT(*) as count FROM t_tag t , t_blog b , t_blog_tags g " +
            "WHERE t.id = g.tags_id AND b.id = g.blogs_id AND b.published = 1 " +
            "GROUP BY t.id ORDER BY count DESC LIMIT ?1"
            ,nativeQuery = true)
    List<Map> findTop(Integer size);
}
