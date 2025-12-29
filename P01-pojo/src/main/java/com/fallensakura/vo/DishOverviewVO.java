package com.fallensakura.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class DishOverviewVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer discontinued;

    private Integer sold;
}
