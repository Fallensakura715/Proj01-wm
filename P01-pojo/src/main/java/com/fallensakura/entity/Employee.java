package com.fallensakura.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "Employee", description = "员工实体类")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idNumber;

    /**
     * 1正常 0锁定
     */
    @Schema(description = "1正常 0锁定")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "最后修改时间")
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人id")
    private Long createUser;

    /**
     * 最后修改人id
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "最后修改人id")
    private Long updateUser;
}
