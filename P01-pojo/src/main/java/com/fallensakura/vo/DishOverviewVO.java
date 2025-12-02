package com.fallensakura.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DishOverviewVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer discontinued;

    private Integer sold;
}
