package org.diverbee.controller;

import org.diverbee.pojo.Category;
import org.diverbee.pojo.Result;
import org.diverbee.service.CategoryService;
import org.diverbee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category> > list(){
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result detail(Integer id){
        Category category = categoryService.detailById(id);
        if(category==null){
            return Result.error("分类不存在!");
        }
        return Result.success(category);
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
    @DeleteMapping
    public Result del(@RequestParam Integer id){
        categoryService.delById(id);
        return Result.success();
    }
}
