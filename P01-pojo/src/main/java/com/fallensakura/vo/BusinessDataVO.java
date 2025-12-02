package com.fallensakura.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BusinessDataVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer newUsers;

    private double orderCompletionRate;

    private BigDecimal turnover;

    private BigDecimal unitPrice;

    private Integer validOrderCount;
}
