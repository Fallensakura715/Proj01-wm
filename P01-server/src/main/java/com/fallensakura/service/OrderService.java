package com.fallensakura.service;

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
}
