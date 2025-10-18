package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Schema(description = "员工DTO")
public class EmployeeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


}
