package cn.cjun.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author 戴智钧
 * @Date 2019/12/24 16:02
 */
@Controller
public class AboutController {

    @GetMapping("about")
    public String archives() {
        return "about";
    }
}
