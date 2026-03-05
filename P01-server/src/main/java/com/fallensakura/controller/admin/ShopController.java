package com.fallensakura.controller.admin;

import com.fallensakura.result.Result;
import com.fallensakura.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-12-29
 */
@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Tag(name = "店铺接口")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/status")
    @Operation(summary = "获取营业状态")
    public Result<?> getStatus() {
        return Result.success(shopService.getStatus());
    }

    @PutMapping("/{status}")
    @Operation(summary = "设置营业状态")
    public Result<Integer> setStatus(@PathVariable Integer status) {
        shopService.setStatus(status);
        return Result.success();
    }
}
