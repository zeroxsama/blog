package cn.cjun.blog.pojo;

/**
 * @Author 戴智钧
 * @Date 2019/12/26 10:51
 */


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_music")
public class Music {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String artist;
    private String url;
    private String cover;
    private String lrc;

}
