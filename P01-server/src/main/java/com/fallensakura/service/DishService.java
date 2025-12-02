package com.fallensakura.service;

import com.fallensakura.dto.DishDTO;
import com.fallensakura.dto.DishPageQueryDTO;
import com.fallensakura.entity.Dish;
import com.fallensakura.result.PageResult;
import com.fallensakura.vo.DishVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface DishService {
    /**
     * 修改菜品
     * @param dto
     */
    void update(DishDTO dto);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 新增菜品
     * @param dto
     */
    void addDish(DishDTO dto);

    /**
     * 根据ID查询菜品
     * @param id
     * @return DishVO
     */
    DishVO selectById(Long id);

    /**
     * 根据分类ID查询菜品
     * @param categoryId
     * @return List<Dish>
     */
    List<Dish> selectByCategoryId(Long categoryId);

    /**
     * 菜品分页查询
     * @param dto
     * @return PageResult<DishVO>
     */
    PageResult<DishVO> selectPage(DishPageQueryDTO dto);

    /**
     * 菜品起售、停售
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);
}
