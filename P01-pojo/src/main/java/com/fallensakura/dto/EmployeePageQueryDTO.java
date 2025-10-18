package com.fallensakura.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private int page;
    private int pageSize;
}
