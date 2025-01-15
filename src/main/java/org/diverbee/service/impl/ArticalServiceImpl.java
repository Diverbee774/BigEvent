package org.diverbee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.diverbee.mapper.ArticleMapper;
import org.diverbee.pojo.Article;
import org.diverbee.pojo.PageBean;
import org.diverbee.service.ArticalService;
import org.diverbee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticalServiceImpl implements ArticalService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");

        article.setCreateUser(userid);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(
            Integer pageNum,
            Integer pageSize,
            Integer categoryId,
            String state
    ) {
        PageBean<Article> pb = new PageBean<>();
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");

        PageHelper.startPage(pageNum,pageSize);

        List<Article> as = articleMapper.list(userid,categoryId,state);

        Page<Article> p = (Page<Article>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public Article detail(Integer articleId) throws Exception {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        Article article = articleMapper.detail(userId,articleId);
        if(article==null){
            throw new Exception("文章不存在!");
        }

        return article;
    }

    @Override
    public void update(Article article) throws Exception {
        Article old = detail(article.getId());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        if(!old.getCreateUser().equals(userId)){
            throw new Exception("非法操作!");
        }

        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void del(Integer id) throws Exception {
        Article old = detail(id);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        if(!old.getCreateUser().equals(userId)){
            throw new Exception("非法操作!");
        }
        articleMapper.del(id);
    }
}
