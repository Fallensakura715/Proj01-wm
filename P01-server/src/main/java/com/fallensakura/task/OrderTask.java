package com.fallensakura.task;

import com.fallensakura.constant.OrderConstant;
import com.fallensakura.entity.Order;
import com.fallensakura.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderTask {

    private final OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * *")
    public void processTimeoutOrder() {
        log.info("Process timeout orders: {}", new Date());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        List<Order> orderList = orderMapper.getByStatusAndOrdertimeLT(OrderConstant.PENDING_PAYMENT, time);
        if (orderList != null && !orderList.isEmpty()) {
            orderList.forEach(order -> {
                order.setStatus(OrderConstant.CANCELLED);
                order.setCancelReason("支付超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.updateById(order);
            });
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void processDeliveryOrder() {
        log.info("Process delivery orders: {}", new Date());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Order> orderList = orderMapper.getByStatusAndOrdertimeLT(OrderConstant.DELIVERED, time);

        if (orderList != null && !orderList.isEmpty()) {
            orderList.forEach(order -> {
                order.setStatus(OrderConstant.COMPLETED);
                orderMapper.updateById(order);
            });
        }
    }
}
