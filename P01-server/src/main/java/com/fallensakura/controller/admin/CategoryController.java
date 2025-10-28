package com.fallensakura.controller.admin;

import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.dto.CategoryPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.result.PageResult;
import com.fallensakura.result.Result;
import com.fallensakura.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "分类接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PutMapping
    @Operation(summary = "修改分类")
    Result<?> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分类分页查询")
    Result<PageResult<Category>> pageQuery(CategoryPageQueryDTO dto) {
        return Result.success(categoryService.pageQuery(dto));
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "启用、禁用分类")
    Result<?> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        categoryService.updateStatus(status, id);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "新增分类")
    Result<?> addCategory(@RequestBody CategoryDTO dto) {
        categoryService.addCategory(dto);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "根据ID删除分类")
    Result<?> deleteById(Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类")
    Result<List<Category>> selectByType(Integer type) {
        return Result.success(categoryService.selectByType(type));
    }

}
