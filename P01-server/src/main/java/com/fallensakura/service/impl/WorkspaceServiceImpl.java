package com.fallensakura.service.impl;

import com.fallensakura.mapper.OrderMapper;
import com.fallensakura.mapper.UserMapper;
import com.fallensakura.service.WorkspaceService;
import com.fallensakura.vo.BusinessDataVO;
import com.fallensakura.vo.DishOverviewVO;
import com.fallensakura.vo.OrderOverviewVO;
import com.fallensakura.vo.SetmealOverviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

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
        return null;
    }

    @Override
    public DishOverviewVO selectOverviewDishes() {
        return null;
    }

    @Override
    public OrderOverviewVO selectOverviewOrders() {
        return null;
    }
}
