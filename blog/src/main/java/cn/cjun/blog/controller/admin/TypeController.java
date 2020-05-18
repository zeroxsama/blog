package cn.cjun.blog.controller.admin;

import cn.cjun.blog.entity.Result;
import cn.cjun.blog.pojo.Type;
import cn.cjun.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @PostMapping("types")
    @ResponseBody
    public Result saveType(@RequestBody Type type) {
        Type t = typeService.saveType(type);
        if (t == null) {
            return new Result(false, "操作失败,该数据已经存在");
        }
        return new Result(true, "操作成功");
    }

    @DeleteMapping("types/{id}")
    @ResponseBody
    public Result deleteType(@PathVariable Long id) {
        try {
            typeService.deleteType(id);
        } catch (Exception e) {
            return new Result(false, "操作失败,已有文章使用该分类!");
        }
        return new Result(true, "操作成功");
    }

    @GetMapping("types/input")
    public String input( Model model) {
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("types/{id}")
    public String input(@PathVariable Long id, Model model) {
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
}
