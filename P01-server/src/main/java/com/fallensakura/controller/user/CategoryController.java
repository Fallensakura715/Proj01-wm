package com.fallensakura.controller.user;

import com.fallensakura.entity.Category;
import com.fallensakura.result.Result;
import com.fallensakura.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@RequiredArgsConstructor
@Tag(name = "C端-分类接口")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "分类条件查询")
    public Result<List<Category>> getCategoryList(@RequestParam Integer type) {
        return Result.success(categoryService.selectByType(type));
    }
}
