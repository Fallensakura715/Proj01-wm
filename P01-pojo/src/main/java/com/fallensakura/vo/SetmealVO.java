package com.fallensakura.vo;

import com.fallensakura.entity.SetmealDish;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SetmealVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    private Integer status;

    private String description;

    private String image;

    private LocalDateTime updateTime;

    private String categoryName;

    private List<SetmealDish> setmealDishes;
}
