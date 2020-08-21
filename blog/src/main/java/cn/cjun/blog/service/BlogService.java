package cn.cjun.blog.service;

import cn.cjun.blog.dao.BlogDao;
import cn.cjun.blog.dao.MusicDao;
import cn.cjun.blog.pojo.Blog;
import cn.cjun.blog.pojo.BlogQuery;
import cn.cjun.blog.pojo.Music;
import cn.cjun.blog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author 戴智钧
 * @Date 2019/12/22 18:13
 */
@Service
@Transactional
public class BlogService {
    @Autowired
    private BlogDao blogDao;

    public Blog getAndConvert(Long id) {

        Blog blog = blogDao.findOne(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate1 = cb.equal(root.get("id"), id);
                Predicate predicate2 = cb.equal(root.get("published"), true);
                cq.where(predicate1, predicate2);
                return null;
            }
        }).get();

        blog.setViews(blog.getViews() + 1);//浏览次数加1  Hibernate session 中 会自动执行更新操作

        return blog;
    }

    public Blog saveBlog(Blog blog) {
        //初始化设置   文章存在 则修改操作 只更新修改时间
        Long id = blog.getId();
        blog.setUpdateTime(new Date()); //修改时间
        if (id != null) {
            Blog one = blogDao.getOne(id);
            blog.setCreateTime(one.getCreateTime());
            blog.setViews(one.getViews());
        } else {
            blog.setCreateTime(new Date()); //创建时间
            blog.setViews(0); //浏览次数
        }
        return blogDao.saveAndFlush(blog);
    }

    public void deleteBlog(Long id) {
        blogDao.deleteById(id);
    }

    public Blog getBlog(Long id) {
        return blogDao.findById(id).get();
    }

    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cq.where(cb.equal(root.get("published"), true));
                return null;
            }
        }, pageable);
    }

    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable p = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogDao.findRecommendBlogTop(p);

    }

    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query, pageable);
    }

    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate1 = cb.equal(root.get("published"), true);
                Predicate predicate2 = cb.equal(root.join("tags").get("id"), tagId);
                cq.where(predicate1, predicate2);
                return null;
            }
        }, pageable);
    }

    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    public Long findCount() {
        return blogDao.count(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate1 = cb.equal(root.get("published"), true);
                cq.where(predicate1);
                return null;
            }
        });
    }


    @Autowired
    private MusicDao musicDao;

    public List<Music> findAllMusic() {
        return musicDao.findAll();
    }

    public Music saveMusic(Music music) {
        return musicDao.save(music);
    }

    public Page<Blog> listBlog(Pageable pageable, Long typeId) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate predicate1 = cb.equal(root.get("published"), true);
                Predicate predicate2 = cb.equal(root.join("type").get("id"), typeId);
                cq.where(predicate1, predicate2);
                return null;
            }
        }, pageable);
    }

    public void operateBlog(List<Long> ids, boolean bool) {
        for (Long id : ids) {
            Blog blog = blogDao.findById(id).get();
            blog.setPublished(bool);
            blog.setUpdateTime(new Date());
        }
    }

    public void updateQiniu(Map<String, String> map) {
        String beforeUrl = map.get("beforeUrl");
        String afterUrl = map.get("afterUrl");
        List<Blog> blogs = blogDao.findAll();
        if (!CollectionUtils.isEmpty(blogs)) {
            blogs.forEach(blog -> blog.setContent(blog.getContent().contains(beforeUrl)
                    ? blog.getContent().replace(beforeUrl, afterUrl) : blog.getContent()));
        }

    }
}
