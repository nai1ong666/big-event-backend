package com.itheima.controller;

import com.itheima.annotation.RequireAdmin;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService ariticalService;

    @PostMapping

    public Result add(@RequestBody @Validated Article article){

        ariticalService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = ariticalService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);

    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam @Validated Integer id){
        Article article = ariticalService.findById(id);
        return Result.success(article);
    }

    @PutMapping

    public Result update(@RequestBody @Validated Article article){
        ariticalService.update(article);
        return Result.success();
    }

    @DeleteMapping

    public Result delete(@RequestParam Integer id){
        Article article = ariticalService.findById(id);
        if (article == null){
            return Result.error("该文章不存在");
        }
        ariticalService.delete(id);
        return Result.success();
    }

}
