package org.diverbee.service;

import org.diverbee.pojo.Category;

import java.util.List;

public interface CategoryService {

    //新增分类
    void add(Category category);

    //查询分类，返回分类列表
    List<Category> list();

    //根据ID找到信息
    Category findById(Integer id);

    void update(Category category);

    //得到用户所有分类中id为#{id}的分类
    Category detailById(Integer id);

    //删除用户的#{id}分类
    void delById(Integer id);
}
