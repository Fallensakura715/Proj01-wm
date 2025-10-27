package com.fallensakura.controller.admin;

import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.result.Result;
import com.fallensakura.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/")
    @Operation(summary = "修改分类")
    Result<?> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }
}
