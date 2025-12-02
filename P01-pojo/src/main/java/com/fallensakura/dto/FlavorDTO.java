package com.fallensakura.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FlavorDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long dishId;

    private Long id;

    private String name;

    private String value;
}
