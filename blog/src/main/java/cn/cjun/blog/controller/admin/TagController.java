package cn.cjun.blog.controller.admin;

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
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
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
    public String input( Model model) {
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("tags/{id}")
    public String input(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }
}
