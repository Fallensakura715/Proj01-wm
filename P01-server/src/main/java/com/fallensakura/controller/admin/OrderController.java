package com.fallensakura.controller.admin;

import com.fallensakura.dto.OrderCancellationDTO;
import com.fallensakura.dto.OrderConditionSearchDTO;
import com.fallensakura.dto.OrderRejectionDTO;
import com.fallensakura.result.Result;
import com.fallensakura.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/cancel")
    @Operation(summary = "取消订单")
    public Result<?> cancelOrder(@RequestBody OrderCancellationDTO dto) {
        orderService.cancelOrder(dto);
        return Result.success();
    }

    @GetMapping("/statistics")
    @Operation(summary = "各个状态的订单数量统计")
    public Result<?> getOrderStatistics() {
        return Result.success(orderService.getOrderStatistics());
    }

    @PutMapping("/complete/{id}")
    @Operation(summary = "完成订单")
    public Result<?> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return Result.success();
    }

    @PutMapping("/rejection")
    @Operation(summary = "拒单")
    public Result<?> rejectOrder(@RequestBody OrderRejectionDTO dto) {
        orderService.rejectOrder(dto);
        return Result.success();
    }

    @PutMapping("/confirm")
    @Operation(summary = "接单")
    public Result<?> acceptOrder(@RequestBody Long id) {
        orderService.acceptOrder(id);
        return Result.success();
    }

    @GetMapping("/details/{id}")
    @Operation(summary = "查询订单详情")
    public Result<?> getOrderDetails(@PathVariable Long id) {
        return Result.success(orderService.getOrderDetails(id));
    }

    @PutMapping("/delivery/{id}")
    @Operation(summary = "派送订单")
    public Result<?> deliverOrder(@PathVariable Long id) {
        orderService.deliverOrder(id);
        return Result.success();
    }

    @GetMapping("/conditionSearch")
    @Operation(summary = "订单搜索")
    public Result<?> searchOrder(@Valid OrderConditionSearchDTO dto) {
        return Result.success(orderService.getOrderStatistics());
    }
}
