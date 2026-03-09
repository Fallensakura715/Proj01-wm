package com.fallensakura.controller.user;

import com.fallensakura.dto.ShoppingCartDTO;
import com.fallensakura.entity.ShoppingCart;
import com.fallensakura.result.Result;
import com.fallensakura.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/user/shoppingCart")
@RequiredArgsConstructor
@Tag(name = "C端-购物车接口")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/sub")
    @Operation(summary = "删除购物车中一个商品")
    public Result<?> deleteOne(@RequestBody ShoppingCartDTO dto) {
        shoppingCartService.deleteOne(dto);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(summary = "查看购物车")
    public Result<List<ShoppingCart>> shoppingCartList() {
        return Result.success(shoppingCartService.selectList());
    }

    @PostMapping("/add")
    @Operation(summary = "添加购物车")
    public Result<?> add(ShoppingCartDTO dto) {
        shoppingCartService.add(dto);
        return Result.success();
    }

    @DeleteMapping("/clean")
    @Operation(summary = "清空购物车")
    public Result<?> clean() {
        shoppingCartService.clean();
        return Result.success();
    }

}
