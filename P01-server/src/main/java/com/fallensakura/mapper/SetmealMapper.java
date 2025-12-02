package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.annotation.AutoFill;
import com.fallensakura.dto.SetmealPageQueryDTO;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.enumeration.OperationType;
import com.fallensakura.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @AutoFill(OperationType.UPDATE)
    @Override
    int updateById(Setmeal setmeal);

    @AutoFill(OperationType.INSERT)
    @Override
    int insert(Setmeal setmeal);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    Page<SetmealVO> selectPageVO(Page<SetmealVO> page, @Param("dto") SetmealPageQueryDTO dto);
}
