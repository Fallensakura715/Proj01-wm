package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.dto.CategoryPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.entity.Dish;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.CategoryMapper;
import com.fallensakura.mapper.DishMapper;
import com.fallensakura.mapper.SetmealMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    @Transactional
    @Override
    public void update(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        categoryMapper.update(category);
    }

    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO dto) {
        Page<Category> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<Category> categoryPage = categoryMapper.selectPage(page, dto);
        return new PageResult<>(categoryPage.getTotal(), categoryPage.getRecords());
    }

    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.update(category);
    }

    @Transactional
    @Override
    public void addCategory(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        category.setStatus(1);
        categoryMapper.insert(category);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        if (dishMapper.selectCount(
                new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, id)
        ) > 0) {
            throw new BusinessException("当前分类有菜品，不能删除");
        }

        if (setmealMapper.selectCount(
                new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId, id)
        ) > 0) {
            throw new BusinessException("当前分类下有套餐，不能删除");
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> selectByType(Integer type) {
        return categoryMapper.selectByType(type);
    }

}
