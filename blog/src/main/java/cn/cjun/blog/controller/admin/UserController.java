package cn.cjun.blog.controller.admin;

import cn.cjun.blog.entity.Result;
import cn.cjun.blog.pojo.User;
import cn.cjun.blog.rsa.RsaKeys;
import cn.cjun.blog.service.RsaService;
import cn.cjun.blog.service.UserService;
import cn.cjun.blog.uitl.Md5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author 戴智钧
 * @Date 2019/12/21 21:36
 */
@Controller
@RequestMapping("admin")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RsaService rsaService;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:admin/blogs";
        }
        return "admin/login";
    }

    @GetMapping("index")
    public String index() {
        return "admin/index";
    }

    @GetMapping("login")
    @ResponseBody
    public Result getkey() {
        return new Result(true, "操作成功", RsaKeys.getServerPubKey());
    }

    @PostMapping("login")
    @ResponseBody
    public Result login(HttpServletRequest request, HttpSession session) {
        String decryptData = null;
        ServletInputStream is = null;
        try {
            is = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            //解密
            decryptData = rsaService.RSADecryptDataPEM(sb.toString(), RsaKeys.getServerPrvKeyPkcs8());
            System.out.println(decryptData);
            User temp = objectMapper.readValue(decryptData, User.class);

            User user = userService.checkUser(temp.getUsername(),
                    Md5Util.md5(temp.getPassword()));//md5 加密密码

            if (user != null) {
                //登录成功  存入session
                user.setPassword(null);
                session.setAttribute("user", user);
                return new Result(true, "登录成功");
            } else {
                return new Result(false, "操作失败,用户名或密码错误!");
            }

        } catch (Exception e) {
            return new Result(false, "非法操作");
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
