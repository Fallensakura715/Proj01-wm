package com.fallensakura.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private String name;

    private Integer page;

    private Integer pageSize;

    private Integer status;
}
