package com.fallensakura.service.impl;

import com.fallensakura.constant.OrderConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.entity.Order;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.OrderMapper;
import com.fallensakura.service.OrderService;
import com.fallensakura.websocket.WebSocketServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WebSocketServer webSocketServer;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    @Transactional
    public void paySuccess(String outTradeNo) {

        Order ordersDB = orderMapper.selectById(outTradeNo);

        if (ordersDB == null) {
            throw new BusinessException("订单不存在，订单号：{}" + outTradeNo);
        }

        Order orders = Order.builder()
                .id(ordersDB.getId())
                .status(OrderConstant.PENDING_ORDER)
                .paymentStatus(OrderConstant.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.updateById(orders);

        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", outTradeNo);

        webSocketServer.sendToAllClient(objectMapper.writeValueAsString(map));
    }
}
