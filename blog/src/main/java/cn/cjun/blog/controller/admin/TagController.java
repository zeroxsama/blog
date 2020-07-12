package cn.cjun.blog.controller.admin;

import cn.cjun.blog.entity.Page;
import cn.cjun.blog.entity.Result;
import cn.cjun.blog.pojo.Tag;
import cn.cjun.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 12:42
 */
@Controller
@RequestMapping("admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        org.springframework.data.domain.Page<Tag> tags = tagService.listTag(pageable);
        page(pageable, model, tags);
        model.addAttribute("page", tags);
        return "admin/tags";
    }

    private void page(Pageable pageable, Model model, org.springframework.data.domain.Page<Tag> tags) {
        //封装分页对象
        Page<Tag> page = new Page<>(tags.getTotalElements(),
                pageable.getPageNumber() + 1,
                Page.pageSize, 3);

        model.addAttribute("pageB", page);
    }

    @PostMapping("tags")
    @ResponseBody
    public Result saveTag(@RequestBody Tag tag) {
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            return new Result(false, "操作失败,该数据已经存在");
        }
        return new Result(true, "操作成功");
    }

    @DeleteMapping("tags/{id}")
    @ResponseBody
    public Result deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
        } catch (Exception e) {
            return new Result(false, "操作失败,已有文章使用该标签!");
        }
        return new Result(true, "操作成功");
    }

    @GetMapping("tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("tags/{id}")
    public String input(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }
}
