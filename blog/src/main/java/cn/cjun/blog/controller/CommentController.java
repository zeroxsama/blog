package cn.cjun.blog.controller;


import cn.cjun.blog.pojo.Comment;
import cn.cjun.blog.pojo.User;
import cn.cjun.blog.service.BlogService;
import cn.cjun.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author 戴智钧
 * @Date 2019/12/24 10:18
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("comments/{id}")
    public String comments(@PathVariable Long id, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        return "blog :: commentList";
    }


    @PostMapping("comments")
    public String comments(Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //管理员登录 取头像 标记是管理员
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);//设置头像
        }

        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));

        commentService.saveComment(comment);

        return "redirect:/comments/" + blogId;
    }
}
