package com.fallensakura.controller.admin;

import com.fallensakura.dto.DishDTO;
import com.fallensakura.dto.DishPageQueryDTO;
import com.fallensakura.entity.Dish;
import com.fallensakura.result.PageResult;
import com.fallensakura.result.Result;
import com.fallensakura.service.DishService;
import com.fallensakura.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/admin/dish")
@Tag(name = "菜品接口")
@Slf4j
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PutMapping
    @Operation(summary = "修改菜品")
    public Result<String> update(@RequestBody DishDTO dto) {
        dishService.update(dto);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "批量删除菜品")
    public Result<String> deleteByIds(@RequestParam String ids) {
        dishService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "新增菜品")
    public Result<String> addDish(@RequestBody DishDTO dto) {
        dishService.addDish(dto);
        return Result.success();
    }

    @GetMapping("/dish/{id}")
    @Operation(summary = "根据ID查询菜品")
    public Result<DishVO> selectById(@PathVariable Long id) {
        return Result.success(dishService.selectById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "根据分类ID查询菜品")
    public Result<List<Dish>> selectByCategoryId(@RequestParam Long categoryId) {
        return Result.success(dishService.selectByCategoryId(categoryId));
    }

    @GetMapping("/page")
    @Operation(summary = "菜品分页查询")
    public Result<PageResult<DishVO>> selectPage(DishPageQueryDTO dto) {
        return Result.success(dishService.selectPage(dto));
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "菜品起售、停售")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        dishService.updateStatus(status, id);
        return Result.success();
    }
}
