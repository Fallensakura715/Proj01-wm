package com.fallensakura.service;

import com.fallensakura.vo.BusinessDataVO;
import com.fallensakura.vo.DishOverviewVO;
import com.fallensakura.vo.OrderOverviewVO;
import com.fallensakura.vo.SetmealOverviewVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-11-11
 */
public interface WorkspaceService {

    /**
     * 查询今日运营数据
     * @return BusinessDataVO
     */
    BusinessDataVO selectBusinessData();

    /**
     * 查询套餐总览
     * @return SetmealOverviewVO
     */
    SetmealOverviewVO selectOverviewSetmeals();

    /**
     * 查询菜品总览
     * @return DishOverviewVO
     */
    DishOverviewVO selectOverviewDishes();

    /**
     * 查询订单管理数据
     * @return OrderOverviewVO
     */
    OrderOverviewVO selectOverviewOrders();
}
