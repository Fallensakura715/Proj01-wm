package com.fallensakura.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer sort;

    //1为菜品分类，2为套餐分类
    private Integer type;
}
