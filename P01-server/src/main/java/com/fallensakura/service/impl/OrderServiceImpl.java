package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.constant.OrderConstant;
import com.fallensakura.dto.OrderCancellationDTO;
import com.fallensakura.dto.OrderConditionSearchDTO;
import com.fallensakura.dto.OrderRejectionDTO;
import com.fallensakura.entity.Order;
import com.fallensakura.entity.OrderDetail;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.OrderDetailMapper;
import com.fallensakura.mapper.OrderMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.result.Result;
import com.fallensakura.service.OrderService;
import com.fallensakura.vo.OrderDetailsVO;
import com.fallensakura.vo.OrderStatisticsVO;
import com.fallensakura.websocket.WebSocketServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WebSocketServer webSocketServer;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
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

    @Override
    @Transactional
    public void cancelOrder(OrderCancellationDTO dto) {
        Order order = orderMapper.selectById(dto.getId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        order.setCancelTime(LocalDateTime.now());
        order.setStatus(OrderConstant.CANCELLED);
        order.setCancelReason(dto.getCancelReason());
        orderMapper.updateById(order);
    }

    @Override
    public OrderStatisticsVO getOrderStatistics() {
        Integer pendingOrder = orderMapper.countByStatus(OrderConstant.PENDING_ORDER);
        Integer confirmed = orderMapper.countByStatus(OrderConstant.ACCPETED);
        Integer delivering = orderMapper.countByStatus(OrderConstant.DELIVERED);

        return OrderStatisticsVO.builder()
                .toBeConfirmed(pendingOrder)
                .confirmed(confirmed)
                .deliveryInProgress(delivering)
                .build();
    }

    @Override
    @Transactional
    public void completeOrder(Long id) {
        Order order = orderMapper.selectById(id);

        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!(Objects.equals(order.getStatus(), OrderConstant.DELIVERED))) {
            throw new BusinessException("订单状态错误，无法完成");
        }

        Order newOrder = Order.builder()
                .id(id)
                .status(OrderConstant.COMPLETED)
                .build();
        orderMapper.updateById(newOrder);
    }

    @Override
    @Transactional
    public void rejectOrder(OrderRejectionDTO dto) {
        Order order = orderMapper.selectById(dto.getId());

        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!(Objects.equals(order.getStatus(), OrderConstant.PENDING_ORDER))) {
            throw new BusinessException("订单状态错误，无法拒绝");
        }

        Order newOrder = Order.builder()
                .id(dto.getId())
                .status(OrderConstant.CANCELLED)
                .rejectionReason(dto.getRejectionReason())
                .cancelTime(LocalDateTime.now())
                .build();

//        if (OrderConstant.PAID.equals(order.getPaymentStatus())) {
//            // TODO: 调用退款接口
//            log.info("订单已支付，执行退款操作，订单号：{}", order.getNumber());
//            updateOrder.setPaymentStatus(OrderConstant.REFUNDED);
//        }

        orderMapper.updateById(newOrder);
    }

    @Override
    @Transactional
    public void acceptOrder(Long id) {
        Order order = orderMapper.selectById(id);

        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!OrderConstant.PENDING_ORDER.equals(order.getStatus())) {
            throw new BusinessException("订单状态错误，无法接单");
        }

        Order updateOrder = Order.builder()
                .id(id)
                .status(OrderConstant.ACCPETED)
                .build();
        orderMapper.updateById(updateOrder);
    }

    @Override
    public OrderDetailsVO getOrderDetails(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(id);
        OrderDetailsVO vo = new OrderDetailsVO();
        BeanUtils.copyProperties(orderDetails, vo);
        vo.setOrderDetailList(orderDetails);
        return vo;
    }

    @Override
    @Transactional
    public void deliverOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!OrderConstant.ACCPETED.equals(order.getStatus())) {
            throw new BusinessException("订单状态错误，无法派送");
        }

        Order updateOrder = Order.builder()
                .id(id)
                .status(OrderConstant.DELIVERED)
                .build();
        orderMapper.updateById(updateOrder);
    }

    @Override
    public PageResult<OrderDetailsVO> searchOrder(OrderConditionSearchDTO dto) {
        Page<Order> page = new Page<>(dto.getPage(), dto.getPageSize());

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(dto.getPhone()), Order::getReceiverPhone, dto.getPhone())
                .like(StringUtils.hasText(dto.getNumber()), Order::getNumber, dto.getNumber())
                .eq(dto.getStatus() != null, Order::getStatus, dto.getStatus())
                .ge(dto.getBeginTime() != null, Order::getOrderTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, Order::getOrderTime, dto.getEndTime())
                .orderByDesc(Order::getOrderTime);

        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);

        List<OrderDetailsVO> voList = orderPage.getRecords().stream().map(order -> {
            OrderDetailsVO vo = new OrderDetailsVO();
            BeanUtils.copyProperties(order, vo);
            List<OrderDetail> details = orderDetailMapper.selectByOrderId(order.getId());
            vo.setOrderDetailList(details);
            return vo;
        }).toList();

        return new PageResult<>(orderPage.getTotal(), voList);
    }
}
