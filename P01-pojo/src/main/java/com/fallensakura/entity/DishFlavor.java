package com.fallensakura.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Builder
public class DishFlavor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long dishId;

    private Long flavorId;
}
