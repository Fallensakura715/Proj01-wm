package com.fallensakura.controller.admin;

import com.fallensakura.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    public static final String KEY = "SHOP_STATUS";
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/status")
    @Operation(summary = "获取营业状态")
    public Result<?> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        return Result.success(status);
    }

    @PutMapping("/{status}")
    @Operation(summary = "设置营业状态")
    public Result<Integer> setStatus(@PathVariable Integer status) {
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }
}
