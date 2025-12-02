package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    void insertBatch(List<DishFlavor> list);

}
