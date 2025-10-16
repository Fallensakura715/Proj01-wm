package com.fallensakura.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "分类分页查询DTO")
public class CategoryPageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类名")
    private String name;

    @Schema(description = "页码")
    private Integer page;

    //每页记录数
    @Schema(description = "每页记录数")
    private Integer pageSize;

    //1为菜品分类，2为套餐分类
    @Schema(description = "分类")
    private Integer type;
}
