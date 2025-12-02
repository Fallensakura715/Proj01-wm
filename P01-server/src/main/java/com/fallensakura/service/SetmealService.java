package com.fallensakura.service;

import com.fallensakura.dto.SetmealDTO;
import com.fallensakura.dto.SetmealPageQueryDTO;
import com.fallensakura.result.PageResult;
import com.fallensakura.vo.SetmealVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface SetmealService {
    /**
     * 修改套餐
     * @param dto
     */
    void update(SetmealDTO dto);

    /**
     * 分页查询
     * @param dto
     * @return PageResult<SetmealVO>
     */
    PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO dto);

    /**
     * 套餐起售、停售
     * @param status 1表示起售，0表示停售
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 新增套餐
     * @param dto
     */
    void addSetmeal(SetmealDTO dto);

    /**
     * 根据ID查询套餐
     * @param id
     * @return SetmealVO
     */
    SetmealVO selectById(Long id);
}
