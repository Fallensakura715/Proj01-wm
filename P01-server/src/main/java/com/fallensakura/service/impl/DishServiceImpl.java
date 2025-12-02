package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.dto.DishDTO;
import com.fallensakura.dto.DishPageQueryDTO;
import com.fallensakura.dto.FlavorDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.entity.Dish;
import com.fallensakura.entity.DishFlavor;
import com.fallensakura.entity.Flavor;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.CategoryMapper;
import com.fallensakura.mapper.DishFlavorMapper;
import com.fallensakura.mapper.DishMapper;
import com.fallensakura.mapper.FlavorMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.DishService;
import com.fallensakura.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private FlavorMapper flavorMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public void update(DishDTO dto) {
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
                    Flavor newFlavor = Flavor.builder()
                            .name(flavorDTO.getName())
                            .value(flavorDTO.getValue())
                            .build();
                    flavorMapper.insert(newFlavor);
                    flavorId = newFlavor.getId();
                }

                relations.add(DishFlavor.builder()
                                .dishId(dto.getId())
                                .flavorId(flavorId)
                        .build());
            }

            dishFlavorMapper.insertBatch(relations);
        }
    }

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

    @Transactional
    @Override
    public void addDish(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.insert(dish);
    }

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

    @Override
    public List<Dish> selectByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId, categoryId);
        return dishMapper.selectList(wrapper);
    }

    @Override
    public PageResult<DishVO> selectPage(DishPageQueryDTO dto) {
        Page<DishVO> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<DishVO> result = dishMapper.selectPageVO(page, dto);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }
}
