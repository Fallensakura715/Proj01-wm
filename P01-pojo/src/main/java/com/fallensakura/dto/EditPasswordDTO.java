package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Schema(description = "修改密码DTO")
public class EditPasswordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "员工ID")
    private Long employeeId;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "旧密码")
    private String oldPassword;
}
