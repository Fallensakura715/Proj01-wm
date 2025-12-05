package com.fallensakura.controller.admin;

import com.fallensakura.result.Result;
import com.fallensakura.service.WorkspaceService;
import com.fallensakura.vo.BusinessDataVO;
import com.fallensakura.vo.DishOverviewVO;
import com.fallensakura.vo.OrderOverviewVO;
import com.fallensakura.vo.SetmealOverviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
@Tag(name = "工作台接口")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/businessData")
    @Operation(summary = "查询今日运营数据")
    public Result<BusinessDataVO> selectBusinessData() {
        BusinessDataVO vo = workspaceService.selectBusinessData();
        return Result.success(vo);
    }

    @GetMapping("/overviewSetmeals")
    @Operation(summary = "查询套餐总览")
    public Result<SetmealOverviewVO> selectOverviewSetmeals() {
        SetmealOverviewVO vo = workspaceService.selectOverviewSetmeals();
        return Result.success(vo);
    }

    @GetMapping("/overviewDishes")
    @Operation(summary = "查询菜品总览")
    public Result<DishOverviewVO> selectOverviewDishes() {
        DishOverviewVO vo = workspaceService.selectOverviewDishes();
        return Result.success(vo);
    }

    @GetMapping("/overviewOrders")
    @Operation(summary = "查询订单管理数据")
    public Result<OrderOverviewVO> selectOverviewOrders() {
        OrderOverviewVO vo = workspaceService.selectOverviewOrders();
        return Result.success(vo);
    }
}
