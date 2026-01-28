package com.fallensakura.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class OrderStatisticsVO {
    @Schema(description = "待派送数量")
    private Integer confirmed;

    @Schema(description = "派送中数量")
    private Integer deliveryInProgress;

    @Schema(description = "待接单数量")
    private Integer toBeConfirmed;
}