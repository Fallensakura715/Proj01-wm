package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class EditPasswordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long employeeId;

    private String newPassword;

    private String oldPassword;
}
