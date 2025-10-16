package com.fallensakura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Schema(name = "Setmeal", description = "")
public class Setmeal implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long categoryId;

    private String image;

    private BigDecimal price;

    private String description;

    /**
     * 1起售 0停售
     */
    @Schema(description = "1起售 0停售")
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
