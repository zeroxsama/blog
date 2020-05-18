package cn.cjun.blog.controller;

import cn.cjun.blog.entity.Page;
import cn.cjun.blog.pojo.Blog;
import cn.cjun.blog.pojo.BlogQuery;
import cn.cjun.blog.pojo.Tag;
import cn.cjun.blog.service.BlogService;
import cn.cjun.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 12:42
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @GetMapping("tags/{id}")
    public String tag(@PathVariable Long id,
                      @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                              Pageable pageable, Model model) {

        List<Map> tags = tagService.listTagTop(10000);
        if (id == -1) {
            //没选择标签的时候  取第一条标签的id 作为下面 分类对应的博客 查询条件
            id = ((BigInteger) tags.get(0).get("id")).longValue();
        }

        model.addAttribute("tags", tags);

        org.springframework.data.domain.Page<Blog> blogs = blogService.listBlog(id, pageable);
        model.addAttribute("page", blogs);

        //封装分页对象返回
        Page<Blog> page = new Page<>(blogs.getTotalElements(),
                pageable.getPageNumber() + 1,
                8, 3);

        model.addAttribute("pageB", page);
        model.addAttribute("activeTagId", id); //当前选中
        return "tags";
    }
}
