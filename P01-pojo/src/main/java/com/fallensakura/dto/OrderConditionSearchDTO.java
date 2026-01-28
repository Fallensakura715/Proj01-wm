package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema
public class OrderConditionSearchDTO {
    @NotNull(message = "")
    private Integer page;

    @NotNull(message = "")
    private Integer pageSize;

    private String phone;

    private Integer status;

    private String number;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;
}
