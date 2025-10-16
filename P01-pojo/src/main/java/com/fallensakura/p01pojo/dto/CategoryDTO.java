package com.fallensakura.p01pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private int sort;

    //1为菜品分类，2为套餐分类
    private int type;
}
