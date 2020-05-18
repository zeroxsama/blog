package cn.cjun.blog.controller.admin;

import cn.cjun.blog.uitl.QiniuUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author 戴智钧
 * @Date 2019/12/25 7:22
 */

@Controller
@RequestMapping("file")
public class FileUploadController {

    @Value("${qiniu_url}")
    private String qiniu_url;

    //图片上传
    @ResponseBody
    @PostMapping("uploadImage")
    public Map uploadImage(@RequestParam("editormd-image-file") MultipartFile imgFile) {

        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index);

        String fileName = UUID.randomUUID().toString() + extention;

        HashMap<String, Object> res = new HashMap<>();

        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);

            res.put("success", 1);
            res.put("message", "上传成功");
            res.put("url", qiniu_url + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", 0);
            res.put("message", "上传失败");
        }

        return res;
    }


    //音乐上传
    @ResponseBody
    @PostMapping("uploadMusic")
    public Map uploadMusic(@RequestParam("music-file") MultipartFile musicFile) {

        String originalFilename = musicFile.getOriginalFilename();

        HashMap<String, Object> res = new HashMap<>();

        try {
            QiniuUtils.upload2Qiniu(musicFile.getBytes(), originalFilename);

            res.put("success", 1);
            res.put("message", "上传成功");
            res.put("url", qiniu_url + originalFilename);
            res.put("name", originalFilename.split("\\.")[0]);

        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", 0);
            res.put("message", "上传失败");
        }

        return res;
    }

    //歌词上传
    @ResponseBody
    @PostMapping("uploadLrc")
    public Map uploadLrc(@RequestParam("lrc-file") MultipartFile lrcFile) {

        String originalFilename = lrcFile.getOriginalFilename();

        HashMap<String, Object> res = new HashMap<>();

        try {
            QiniuUtils.upload2Qiniu(lrcFile.getBytes(), originalFilename);

            res.put("success", 1);
            res.put("message", "上传成功");
            res.put("url", qiniu_url + originalFilename);

        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", 0);
            res.put("message", "上传失败");
        }

        return res;
    }
}
