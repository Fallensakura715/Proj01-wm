package com.fallensakura.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishPageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private String name;

    private Integer page;

    private Integer pageSize;

    private Integer status;
}
