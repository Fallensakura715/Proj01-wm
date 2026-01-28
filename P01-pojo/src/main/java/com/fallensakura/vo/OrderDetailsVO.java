package com.fallensakura.vo;

import com.fallensakura.entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderDetailsVO {
    private String address;

    private Long addressBookId;

    private BigDecimal amount;

    private String cancelReason;

    private LocalDateTime cancelTime;

    private LocalDateTime checkoutTime;

    private String consignee;

    private Integer deliveryStatus;

    private LocalDateTime deliveryTime;

    private LocalDateTime estimatedDeliveryTime;

    private Long id;

    private String number;

    private List<OrderDetail> orderDetailList;

    private String orderDishes;

    private LocalDateTime orderTime;

    private Integer packAmount;

    private Integer payMethod;

    private Integer payStatus;

    private String phone;

    private String rejectionReason;

    private String remark;

    private Integer status;

    private Integer tablewareNumber;

    private Integer tablewareStatus;

    private Integer userId;

    private String userName;

}
