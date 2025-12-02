package com.fallensakura.dto;

import com.fallensakura.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private String description;

    private Long id;

    private String image;

    private String name;

    private BigDecimal price;

    private List<SetmealDish> setmealDishes;

    private Integer status;
}
