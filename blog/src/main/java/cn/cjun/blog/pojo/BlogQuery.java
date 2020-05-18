package cn.cjun.blog.pojo;

import lombok.Data;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 19:10
 */
@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
