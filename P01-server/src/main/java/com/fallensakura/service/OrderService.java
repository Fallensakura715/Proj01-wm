package com.fallensakura.service;

import com.fallensakura.dto.OrderCancellationDTO;
import com.fallensakura.dto.OrderConditionSearchDTO;
import com.fallensakura.dto.OrderRejectionDTO;
import com.fallensakura.result.PageResult;
import com.fallensakura.vo.OrderDetailsVO;
import com.fallensakura.vo.OrderStatisticsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface OrderService {

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 取消订单
     * @param dto
     */
    void cancelOrder(OrderCancellationDTO dto);

    /**
     * 各个状态的订单数量统计
     */
    OrderStatisticsVO getOrderStatistics();

    /**
     * 完成订单
     * @param id
     */
    void completeOrder(Long id);

    /**
     * 拒单
     * @param dto
     */
    void rejectOrder(OrderRejectionDTO dto);

    /**
     * 接单
     * @param id
     */
    void acceptOrder(Long id);

    /**
     * 查询订单详情
     * @param id
     */
    OrderDetailsVO getOrderDetails(Long id);

    /**
     * 派送订单
     * @param id
     */
    void deliverOrder(Long id);

    /**
     * 订单搜索
     * @param dto
     */
    PageResult<OrderDetailsVO> searchOrder(OrderConditionSearchDTO dto);
}
