package com.fallensakura.controller.user;

import com.fallensakura.entity.Dish;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.result.Result;
import com.fallensakura.service.DishService;
import com.fallensakura.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
@RequiredArgsConstructor
@Tag(name = "C端-套餐浏览接口")
public class SetmealController {

    private final SetmealService setmealService;
    private final DishService dishService;

    @GetMapping("/list")
    @Operation(summary = "根据分类id查询套餐")
    public Result<List<Setmeal>> getSetmealByCategoryId(@RequestParam Long categoryId) {
        return Result.success(setmealService.selectByCategoryId(categoryId));
    }

    @GetMapping("/dish/{id}")
    @Operation(summary = "根据套餐id查询包含的菜品")
    public Result<List<Dish>> getDishesById(@PathVariable(name = "id") Long setmealId) {
        return Result.success(dishService.selectBySetmealId(setmealId));
    }
}
