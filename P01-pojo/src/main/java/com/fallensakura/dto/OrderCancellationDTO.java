package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class OrderCancellationDTO {
    @Schema(description = "订单id")
    private Long id;

    @Schema(description = "取消原因")
    private String cancelReason;
}
