package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.annotation.AutoFill;
import com.fallensakura.dto.DishDTO;
import com.fallensakura.dto.DishPageQueryDTO;
import com.fallensakura.dto.FlavorDTO;
import com.fallensakura.entity.Dish;
import com.fallensakura.enumeration.OperationType;
import com.fallensakura.vo.DishVO;
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
public interface DishMapper extends BaseMapper<Dish> {

    @AutoFill(OperationType.UPDATE)
    @Override
    int updateById(Dish entity);

    @AutoFill(OperationType.INSERT)
    @Override
    int insert(Dish entity);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<FlavorDTO> selectFlavorsByDishId(Long id);

    Page<DishVO> selectPageVO(Page<DishVO> page, DishPageQueryDTO dto);
}
