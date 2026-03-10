package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.constant.StatusConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.dto.SetmealDTO;
import com.fallensakura.dto.SetmealPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.entity.SetmealDish;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.CategoryMapper;
import com.fallensakura.mapper.SetmealDishMapper;
import com.fallensakura.mapper.SetmealMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.SetmealService;
import com.fallensakura.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
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
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final CategoryMapper categoryMapper;
    private final SetmealDishMapper setmealDishMapper;

    @Caching(evict = {
            @CacheEvict(cacheNames = "setmealByIdCache", key = "#dto.id"),
            @CacheEvict(cacheNames = "setmealListCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    @Transactional
    @Override
    public void update(SetmealDTO dto) {
        if (dto.getId() == null) throw new BusinessException("套餐ID不能为空");

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setUpdateUser(BaseContext.getCurrentId());
        setmealMapper.update(setmeal);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, dto.getId());
        setmealDishMapper.delete(wrapper);

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            return;
        }

        for (SetmealDish dish : setmealDishes) {
            dish.setSetmealId(dto.getId());
            setmealDishMapper.insert(dish);
        }
    }

    @Override
    public PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO dto) {
        Page<SetmealVO> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<SetmealVO> result = setmealMapper.selectPageVO(page, dto);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "setmealByIdCache", key = "#id"),
            @CacheEvict(cacheNames = "setmealListCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(setmeal);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "setmealByIdCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealListCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    @Transactional
    @Override
    public void deleteByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .toList();
        if (!idList.isEmpty()) {
            setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
                    .in(SetmealDish::getSetmealId, idList));
            setmealMapper.deleteByIds(idList);
        }
    }

    @CacheEvict(cacheNames = "setmealListCache", allEntries = true)
    @Transactional
    @Override
    public void addSetmeal(SetmealDTO dto) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            return;
        }

        for (SetmealDish dish : setmealDishes) {
            dish.setSetmealId(setmeal.getId());
            setmealDishMapper.insert(dish);
        }
    }

    @Cacheable(cacheNames = "setmealByIdCache", key = "#id")
    @Override
    public SetmealVO selectById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        if (setmeal == null) {
            throw new BusinessException("套餐不存在");
        }

        SetmealVO vo = new SetmealVO();
        BeanUtils.copyProperties(setmeal, vo);
        Category category = categoryMapper.selectById(vo.getCategoryId());

        if (category != null) {
            vo.setCategoryName(category.getName());
        }

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> dishes = setmealDishMapper.selectList(wrapper);
        vo.setSetmealDishes(dishes);
        return vo;
    }

    @Cacheable(cacheNames = "setmealListCache", key = "#categoryId")
    @Override
    public List<Setmeal> selectByCategoryId(Long categoryId) {
        return setmealMapper.selectList(
                new LambdaQueryWrapper<Setmeal>()
                        .eq(Setmeal::getCategoryId, categoryId)
                        .eq(Setmeal::getStatus, StatusConstant.ENABLE)
        );
    }
}
