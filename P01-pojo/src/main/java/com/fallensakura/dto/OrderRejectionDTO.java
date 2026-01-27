package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class OrderRejectionDTO {
    @Schema(description = "订单id")
    private Long id;

    @Schema(description = "拒单原因")
    private String rejectionReason;
}
