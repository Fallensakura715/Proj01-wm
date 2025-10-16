package com.fallensakura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Schema(name = "Order", description = "")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String number;

    /**
     * 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     */
    @Schema(description = "1待付款 2待接单 3已接单 4派送中 5已完成 6已取消")
    private Byte status;

    private Long userId;

    private Long addressId;

    /**
     * 1立即送出  0选择具体时间
     */
    @Schema(description = "1立即送出  0选择具体时间")
    private Byte deliveryStatus;

    private LocalDateTime deliveryTime;

    private String remark;

    /**
     * 1微信支付 2支付宝支付
     */
    @Schema(description = "1微信支付 2支付宝支付")
    private Byte paymentMethod;

    /**
     * 0未支付 1已支付 2退款
     */
    @Schema(description = "0未支付 1已支付 2退款")
    private Byte paymentStatus;

    private BigDecimal amount;

    private BigDecimal packageAmount;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private String cancelReason;

    private String rejectionReason;

    private LocalDateTime cancelTime;

    private LocalDateTime estimatedDeliveryTime;

    private Integer tablewareNumber;

    /**
     * 1按餐量提供  0选择具体数量
     */
    @Schema(description = "1按餐量提供  0选择具体数量")
    private Byte tablewareStatus;

    private String receiverName;

    private String receiverPhone;
}
