package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private int page;

    private int pageSize;
}
