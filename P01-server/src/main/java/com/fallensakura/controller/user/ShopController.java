package com.fallensakura.controller.user;

import com.fallensakura.result.Result;
import com.fallensakura.service.ShopService;
import com.fallensakura.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop")
@RequiredArgsConstructor
@Tag(name = "C端-店铺操作接口")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/status")
    @Operation(summary = "获取营业状态", description = "店铺状态：1为营业，0为打烊")
    public Result<Integer> getShopStatus() {
        return Result.success(shopService.getStatus());
    }
}
