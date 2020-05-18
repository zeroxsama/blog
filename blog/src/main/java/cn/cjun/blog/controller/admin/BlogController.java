package cn.cjun.blog.controller.admin;

import cn.cjun.blog.entity.Page;
import cn.cjun.blog.entity.Result;
import cn.cjun.blog.pojo.Blog;
import cn.cjun.blog.pojo.BlogQuery;
import cn.cjun.blog.pojo.Music;
import cn.cjun.blog.pojo.User;
import cn.cjun.blog.service.BlogService;
import cn.cjun.blog.service.TagService;
import cn.cjun.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 11:12
 */
@Controller
@RequestMapping("admin")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @GetMapping("uploadfiles")
    public String uploadfiles() {
        return "admin/uploadfiles";
    }

    @GetMapping("blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, BlogQuery blog, Model model) {
        org.springframework.data.domain.Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("page", blogs);
        page(pageable, model, blogs);
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    @PostMapping("blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, BlogQuery blog, Model model) {
        org.springframework.data.domain.Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("page", blogs);
        page(pageable, model, blogs);
        return "admin/blogs :: blogList";
    }

    private void page(Pageable pageable, Model model, org.springframework.data.domain.Page<Blog> blogs) {
        //封装分页对象
        Page<Blog> page = new Page<>(blogs.getTotalElements(),
                pageable.getPageNumber() + 1,
                Page.pageSize, 3);

        model.addAttribute("pageB", page);
    }


    @PostMapping("blogs")
    public String saveBlog(Blog blog, HttpSession session) {
        System.out.println(blog);
        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            throw new RuntimeException("操作有误");
        }
        return REDIRECT_LIST;
    }

    @PostMapping("saveMusic")
    @ResponseBody
    public Result saveMusic(Music music) {
        Music m = blogService.saveMusic(music);
        if (m == null) {
            throw new RuntimeException("操作失败");
        }
        return new Result(true, "操作成功");
    }

    @GetMapping("blogs/input")
    public String input(Model model) {
        setTypesAndTags(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypesAndTags(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("blogs/{id}")
    public String input(@PathVariable Long id, Model model) {
        setTypesAndTags(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @DeleteMapping("blogs/{id}")
    @ResponseBody
    public Result deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return new Result(true, "操作成功");
    }

}
