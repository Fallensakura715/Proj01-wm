package com.fallensakura.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class OrderOverviewVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer allOrders;

    private Integer cancelledOrders;

    private Integer completedOrders;

    private Integer deliveredOrders;

    private Integer waitingOrders;
}
