package cn.cjun.blog.controller;

import cn.cjun.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author 戴智钧
 * @Date 2019/12/24 15:38
 */
@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.findCount());
        return "archives";
    }
}
