package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


/**
 * @Author 戴智钧
 * @Date 2019/12/22 12:33
 */
public interface TypeDao extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query(value = "SELECT t.*,count(*) as count FROM t_type t , t_blog b " +
            "WHERE t.id = b.type_id AND b.published = 1 " +
            "GROUP BY t.id ORDER BY count DESC LIMIT ?1"
    ,nativeQuery = true)
    List<Map> findTop(Integer size);
}
