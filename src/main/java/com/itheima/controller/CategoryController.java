package com.itheima.controller;


import com.itheima.annotation.RequireAdmin;
import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @RequireAdmin
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();

    }

    @GetMapping
    public Result<List<Category>> list(){

        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){

        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    @RequireAdmin
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();


    }

    @DeleteMapping
    @RequireAdmin
    public Result delete(@RequestParam Integer id){
        Category c = categoryService.findById(id);
        if(c == null){
            return Result.error("不存在此分类");
        }
        categoryService.delete(id);
        return Result.success();
    }

}


