package com.fallensakura.service;

import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.dto.CategoryPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.result.PageResult;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface CategoryService {

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return PageResult<Category>
     */
    PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 根据ID删除分类
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据类型查询分类
     * @param type
     * @return List<Category>
     */
    List<Category> selectByType(Integer type);
}