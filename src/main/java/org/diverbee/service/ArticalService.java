package org.diverbee.service;

import org.diverbee.pojo.Article;
import org.diverbee.pojo.PageBean;

public interface ArticalService {







    //添加一篇新的文章
    void add(Article article);

    //根据条件去查询文章列表 分页
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //获取对应id的文章
    Article detail(Integer articleId) throws Exception;

    //更新对应文章
    void update(Article article) throws Exception;

    void del(Integer id) throws Exception;
}
