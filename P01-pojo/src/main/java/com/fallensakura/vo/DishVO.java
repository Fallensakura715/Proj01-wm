package com.fallensakura.vo;

import com.fallensakura.dto.FlavorDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DishVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private BigDecimal price;

    private String image;

    private String description;

    private Long categoryId;

    private String categoryName;

    private Integer status;

    private LocalDateTime updateTime;

    private List<FlavorDTO> flavors;
}
