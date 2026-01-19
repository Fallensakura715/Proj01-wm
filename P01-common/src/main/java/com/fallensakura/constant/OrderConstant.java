package com.fallensakura.constant;

/**
 * 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
 * 0未支付 1已支付 2退款
 * 1微信支付 2支付宝支付
 * 1立即送出  0选择具体时间
 * 1按餐量提供  0选择具体数量
 */

public class OrderConstant {
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer PENDING_ORDER = 2;
    public static final Integer ACCPETED = 3;
    public static final Integer DELIVERED = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    public static final Integer UNPAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    public static final Integer WECHATPAY = 1;
    public static final Integer ALIPAY = 2;

    public static final Integer SELECT_SPECIFIC_TIME = 0;
    public static final Integer DELIVER_IMMEDIATELY = 1;

    public static final Integer ACCORDING_TO_QUANTITY = 1;
    public static final Integer SELECT_SEPECIFIV_QUANTITY = 0;

}
