package com.fallensakura.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@Data
public class BusinessDataVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer newUsers;

    private Double orderCompletionRate;

    private Double turnover;

    private Double unitPrice;

    private Integer validOrderCount;
}
