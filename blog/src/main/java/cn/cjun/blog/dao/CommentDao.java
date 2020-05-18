package cn.cjun.blog.dao;

import cn.cjun.blog.pojo.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 戴智钧
 * @Date 2019/12/24 10:26
 */
public interface CommentDao extends JpaRepository<Comment, Long> {


    List<Comment> findByBlogIdAndParentCommentNullOrderByCreateTimeDesc(Long blogID);
}
