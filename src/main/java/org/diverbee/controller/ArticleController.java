package org.diverbee.controller;
import jakarta.validation.constraints.NotNull;
import org.diverbee.pojo.Article;
import org.diverbee.pojo.PageBean;
import org.diverbee.pojo.Result;
import org.diverbee.service.ArticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticalService articalService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article){
        articalService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = articalService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);

    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam("id") Integer articleId) throws Exception {
        Article article = articalService.detail(articleId);
        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) throws Exception{
        articalService.update(article);
        return Result.success();
    }


    @DeleteMapping
    public Result del(@NotNull Integer id) throws Exception{
        articalService.del(id);
        return Result.success();
    }

}
