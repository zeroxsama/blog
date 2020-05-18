package cn.cjun.blog.service;

import cn.cjun.blog.dao.TagDao;
import cn.cjun.blog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 12:29
 */
@Service
@Transactional
public class TagService {

    @Autowired
    private TagDao tagDao;

    public Tag saveTag(Tag Tag) {
        Tag t = tagDao.findByName(Tag.getName());
        //增加前判断是否存在
        if (t != null) {
            return null;
        }
        return tagDao.saveAndFlush(Tag);
    }

    public void deleteTag(Long id) {
        tagDao.deleteById(id);
    }

    public Tag getTag(Long id) {
        return tagDao.findById(id).get();
    }

    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    public List<Tag> listTag(String ids) {
        return tagDao.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (String s : idarray) {
                try {
                    Long id = Long.valueOf(s);
                    //可能输入的数字标签
                    tagDao.findById(id).get(); //空的  会异常
                    list.add(id);
                } catch (Exception e) {
                    //用户新增的标签  不是数字 肯定会报错
                    Tag tag = new Tag();
                    tag.setName(s);
                    Tag t = tagDao.saveAndFlush(tag);
                    //存入新增后的id
                    list.add(t.getId());
                }
            }
        }
        return list;
    }

    public List<Map> listTagTop(Integer size) {
        return tagDao.findTop(size);
    }
}
