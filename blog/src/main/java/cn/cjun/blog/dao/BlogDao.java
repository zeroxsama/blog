package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 18:13
 */
public interface BlogDao extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true and b.published = true ")
    List<Blog> findRecommendBlogTop(Pageable p);

    @Query("select b from Blog b where (b.title like ?1 or b.content like ?1) and b.published = true ")
    Page<Blog> findByQuery(String query, Pageable pageable);


    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b where b.published = true" +
            " group by function('date_format',b.createTime,'%Y')" +
            " order by function('date_format',b.createTime,'%Y') desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where " +
            "function('date_format',b.createTime,'%Y') = ?1 " +
            "and b.published = true")
    List<Blog> findByYear(String year);
}
