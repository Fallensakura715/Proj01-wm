package com.fallensakura.controller.user;

import com.fallensakura.entity.Dish;
import com.fallensakura.result.Result;
import com.fallensakura.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@RequiredArgsConstructor
@Tag(name = "C端-菜品浏览接口")
public class DishController {

    private final DishService dishService;

    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<Dish>> getDishedByCategoryId(@RequestParam Long id) {
        return Result.success(dishService.selectByCategoryId(id));
    }
}
