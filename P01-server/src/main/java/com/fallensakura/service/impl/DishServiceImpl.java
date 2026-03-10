package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.constant.StatusConstant;
import com.fallensakura.dto.DishDTO;
import com.fallensakura.dto.DishPageQueryDTO;
import com.fallensakura.dto.FlavorDTO;
import com.fallensakura.entity.*;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.*;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.DishService;
import com.fallensakura.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;
    private final FlavorMapper flavorMapper;
    private final CategoryMapper categoryMapper;
    private final SetmealDishMapper setmealDishMapper;

    @Caching(evict = {
            @CacheEvict(cacheNames = "dishByIdCache", key = "#dto.id"),
            @CacheEvict(cacheNames = "dishListCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    @Transactional
    @Override
    public void update(DishDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException("菜品ID不能为空");
        }

        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.update(dish);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        dishFlavorMapper.delete(wrapper);

        List<FlavorDTO> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            List<DishFlavor> relations = new ArrayList<>();
            for (FlavorDTO flavorDTO : flavors) {
                Long flavorId = flavorDTO.getId();
                if (flavorId == null) {
                    Flavor flavor = flavorMapper.selectOne(
                            new LambdaQueryWrapper<Flavor>()
                                    .eq(Flavor::getName, flavorDTO.getName())
                    );
                    if (flavor == null) {
                        Flavor newFlavor = Flavor.builder()
                                .name(flavorDTO.getName())
                                .value(flavorDTO.getValue())
                                .build();
                        flavorMapper.insert(newFlavor);
                        flavorId = newFlavor.getId();
                    } else {
                        flavorId = flavor.getId();
                    }
                }

                relations.add(DishFlavor.builder()
                                .dishId(dto.getId())
                                .flavorId(flavorId)
                        .build());
            }

            dishFlavorMapper.insertBatch(relations);
        }
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "dishByIdCache", allEntries = true),
            @CacheEvict(cacheNames = "dishListCache", allEntries = true),
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
            dishMapper.deleteByIds(idList);
        }
    }

    @CacheEvict(cacheNames = "dishListCache", allEntries = true)
    @Transactional
    @Override
    public void addDish(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.insert(dish);

        List<FlavorDTO> flavors = dto.getFlavors();
        if (flavors == null || flavors.isEmpty()) {
            return;
        }

        List<DishFlavor> relations = new ArrayList<>();
        for (FlavorDTO flavorDTO : flavors) {
            Long flavorId = flavorDTO.getId();
            if (flavorId == null) {
                Flavor flavor = flavorMapper.selectOne(
                        new LambdaQueryWrapper<Flavor>()
                                .eq(Flavor::getName, flavorDTO.getName())
                );
                if (flavor == null) {
                    Flavor newFlavor = Flavor.builder()
                            .name(flavorDTO.getName())
                            .value(flavorDTO.getValue())
                            .build();
                    flavorMapper.insert(newFlavor);
                    flavorId = newFlavor.getId();
                } else {
                    flavorId = flavor.getId();
                }
            }

            relations.add(DishFlavor.builder()
                            .dishId(dish.getId())
                            .flavorId(flavorId)
                    .build());
        }

        dishFlavorMapper.insertBatch(relations);
    }

    @Cacheable(cacheNames = "dishByIdCache", key = "#id")
    @Override
    public DishVO selectById(Long id) {
        Dish dish = dishMapper.selectById(id);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }

        DishVO vo = new DishVO();
        BeanUtils.copyProperties(dish, vo);
        Category category = categoryMapper.selectById(vo.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }

        List<FlavorDTO> flavors = dishMapper.selectFlavorsByDishId(id);
        vo.setFlavors(flavors);
        return vo;
    }

    @Cacheable(cacheNames = "dishListCache", key = "#categoryId == null ? 'all' : #categoryId")
    @Override
    public List<Dish> selectByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();

        if (categoryId == null) {
            wrapper.eq(Dish::getStatus, StatusConstant.ENABLE);
        } else {
            wrapper.eq(Dish::getStatus, StatusConstant.ENABLE)
                    .eq(Dish::getCategoryId, categoryId);
        }

        return dishMapper.selectList(wrapper);
    }

    @Override
    public PageResult<DishVO> selectPage(DishPageQueryDTO dto) {
        Page<DishVO> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<DishVO> result = dishMapper.selectPageVO(page, dto);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "dishByIdCache", key = "#id"),
            @CacheEvict(cacheNames = "dishListCache", allEntries = true),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }

    @Cacheable(cacheNames = "setmealDishCache", key = "#setmealId")
    @Override
    public List<Dish> selectBySetmealId(Long setmealId) {
        List<Long> dishIds = setmealDishMapper.selectList(
                new LambdaQueryWrapper<SetmealDish>()
                        .eq(SetmealDish::getSetmealId, setmealId)
        ).stream().map(SetmealDish::getDishId).toList();

        if (dishIds.isEmpty()) return Collections.emptyList();

        return dishMapper.selectByIds(dishIds);
    }
}
