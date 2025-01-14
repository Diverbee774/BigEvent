package org.diverbee.service.impl;

import lombok.val;
import org.diverbee.mapper.CategoryMapper;
import org.diverbee.pojo.Category;
import org.diverbee.service.CategoryService;
import org.diverbee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(userid);

        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");

        return categoryMapper.list(userid);

    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public Category detailById(Integer id) {
        Category category =  findById(id);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        if(userid.equals(category.getCreateUser())){
            return category;
        }else{
            return null;
        }
    }

    @Override
    public void delById(Integer id) {
        Category category = categoryMapper.findById(id);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        if(category==null||!category.getCreateUser().equals(userid)){
            return;
        }
        categoryMapper.delById(id);
    }
}
