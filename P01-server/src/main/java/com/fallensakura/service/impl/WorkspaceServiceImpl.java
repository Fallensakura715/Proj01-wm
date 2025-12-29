package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fallensakura.constant.OrderConstant;
import com.fallensakura.entity.Dish;
import com.fallensakura.entity.Order;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.mapper.DishMapper;
import com.fallensakura.mapper.OrderMapper;
import com.fallensakura.mapper.SetmealMapper;
import com.fallensakura.mapper.UserMapper;
import com.fallensakura.service.WorkspaceService;
import com.fallensakura.vo.BusinessDataVO;
import com.fallensakura.vo.DishOverviewVO;
import com.fallensakura.vo.OrderOverviewVO;
import com.fallensakura.vo.SetmealOverviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final SetmealMapper setmealMapper;
    private final DishMapper dishMapper;

    @Override
    public BusinessDataVO selectBusinessData() {
        LocalDateTime beginTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Integer newUsers = userMapper.countByCreateTime(beginTime, endTime);
        Integer validOrderCount = orderMapper.countValidOrders(beginTime, endTime);
        Integer totalOrderCount = orderMapper.countTotalOrders(beginTime, endTime);

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != null && totalOrderCount > 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount.doubleValue();
        }

        Double turnover = orderMapper.sumTurnover(beginTime, endTime);
        if (turnover == null) {
            turnover = 0.0;
        }

        Double unitPrice = 0.0;
        if (validOrderCount != null && validOrderCount > 0) {
            unitPrice = turnover / validOrderCount;
        }

        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }

    @Override
    public SetmealOverviewVO selectOverviewSetmeals() {

        Long soldCount = setmealMapper.selectCount(
                new LambdaQueryWrapper<Setmeal>()
                        .eq(Setmeal::getStatus, 1)
        );

        Long discontinuedCount = setmealMapper.selectCount(
                new LambdaQueryWrapper<Setmeal>()
                        .eq(Setmeal::getStatus, 0)
        );

        return SetmealOverviewVO.builder()
                .discontinued(discontinuedCount.intValue())
                .sold(soldCount.intValue())
                .build();
    }

    @Override
    public DishOverviewVO selectOverviewDishes() {

        Long soldCount = dishMapper.selectCount(
                new LambdaQueryWrapper<Dish>()
                        .eq(Dish::getStatus, 1)
        );

        Long discontinuedCount = dishMapper.selectCount(
                new LambdaQueryWrapper<Dish>()
                        .eq(Dish::getStatus, 0)
        );

        return DishOverviewVO.builder()
                .discontinued(discontinuedCount.intValue())
                .sold(soldCount.intValue())
                .build();
    }

    @Override
    public OrderOverviewVO selectOverviewOrders() {

        Long orderCount = orderMapper.selectCount(null);

        Long cancelledOrders = orderMapper.selectCount(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getStatus, OrderConstant.CANCELLED)
        );

        Long completedOrders = orderMapper.selectCount(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getStatus, OrderConstant.COMPLETED)
        );

        Long deliveredOrders = orderMapper.selectCount(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getStatus, OrderConstant.DELIVERED)
        );

        Long waitingOrders = orderMapper.selectCount(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getStatus, OrderConstant.PENDING_ORDER)
        );

        return OrderOverviewVO.builder()
                .allOrders(orderCount.intValue())
                .cancelledOrders(cancelledOrders.intValue())
                .completedOrders(completedOrders.intValue())
                .deliveredOrders(deliveredOrders.intValue())
                .waitingOrders(waitingOrders.intValue())
                .build();
    }
}
