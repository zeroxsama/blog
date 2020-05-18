package cn.cjun.blog.controller;

import cn.cjun.blog.entity.Page;
import cn.cjun.blog.entity.Result;
import cn.cjun.blog.pojo.Blog;
import cn.cjun.blog.service.BlogService;
import cn.cjun.blog.service.TagService;
import cn.cjun.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author 戴智钧
 * @Date 2019/12/21 15:14
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public String index(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        List<Map> types = typeService.listTypeTop(6);
        List<Map> tags = tagService.listTagTop(10);
        List<Blog> recommendBlogs = blogService.listRecommendBlogTop(8);

        org.springframework.data.domain.Page<Blog> blogs = blogService.listBlog(pageable);
        model.addAttribute("page", blogs);

        //封装分页对象返回
        page(pageable, model, blogs);

        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", recommendBlogs);

        return "index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, String query, Model model) {
        org.springframework.data.domain.Page<Blog> blogs = blogService.listBlog("%" + query + "%", pageable);
        model.addAttribute("page", blogs);

        page(pageable, model, blogs);

        model.addAttribute("query", query);
        return "search";
    }

    private void page(Pageable pageable, Model model, org.springframework.data.domain.Page<Blog> blogs) {
        //封装分页对象
        Page<Blog> page = new Page<>(blogs.getTotalElements(),
                pageable.getPageNumber() + 1,
                Page.pageSize, 3);

        model.addAttribute("pageB", page);
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("blog", blogService.getAndConvert(id));
        } catch (Exception e) {
            return "error/404";
        }

        return "blog";
    }

    @GetMapping("/footer/newblogs")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

    @GetMapping("/music")
    @ResponseBody
    public Result findAllMusic() {
        return new Result(true, "查询成功", blogService.findAllMusic());
    }

}
