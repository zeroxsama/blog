package cn.cjun.blog.service;

import cn.cjun.blog.dao.TypeDao;
import cn.cjun.blog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 12:29
 */
@Service
@Transactional
public class TypeService {

    @Autowired
    private TypeDao typeDao;

    public Type saveType(Type type) {
        Type t = typeDao.findByName(type.getName());
        //增加前判断是否存在
        if (t != null) {
            return null;
        }
        return typeDao.saveAndFlush(type);
    }

    public void deleteType(Long id) {
        typeDao.deleteById(id);
    }

    public Type getType(Long id) {
        return typeDao.findById(id).get();
    }

    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    public List<Type> listType() {
        return typeDao.findAll();
    }

    public List<Map> listTypeTop(Integer size) {
        return typeDao.findTop(size);
    }
}
