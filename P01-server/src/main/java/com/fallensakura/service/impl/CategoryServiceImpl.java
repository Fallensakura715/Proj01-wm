package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.context.BaseContext;
import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.dto.CategoryPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.entity.Dish;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.CategoryMapper;
import com.fallensakura.mapper.DishMapper;
import com.fallensakura.mapper.EmployeeMapper;
import com.fallensakura.mapper.SetmealMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Transactional
    @Override
    public void update(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);

        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.updateById(category);
    }

    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO dto) {
        Page<Category> page = new Page<>(dto.getPage(), dto.getPageSize());
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(dto.getName()),
                Category::getName, dto.getName())
                .eq(Category::getType, dto.getType())
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getCreateTime);

        Page<Category> categoryPage = categoryMapper.selectPage(page, wrapper);
        return new PageResult<>(categoryPage.getTotal(), categoryPage.getRecords());
    }

    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.updateById(category);
    }

    @Transactional
    @Override
    public void addCategory(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);

        category.setStatus(1);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

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
