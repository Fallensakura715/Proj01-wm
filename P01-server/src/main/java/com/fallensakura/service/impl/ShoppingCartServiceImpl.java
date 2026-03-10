package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fallensakura.context.BaseContext;
import com.fallensakura.dto.ShoppingCartDTO;
import com.fallensakura.entity.Dish;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.entity.ShoppingCart;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.DishMapper;
import com.fallensakura.mapper.SetmealMapper;
import com.fallensakura.mapper.ShoppingCartMapper;
import com.fallensakura.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    private Long getUserId() {
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) throw new BusinessException("未登录");
        return currentId;
    }

    private void checkDTO(ShoppingCartDTO dto) {
        if (dto == null) {
            throw new BusinessException("参数不能为空");
        }

        boolean hasDishId = dto.getDishId() != null;
        boolean hasSetmealId = dto.getSetmealId() != null;

        if (hasDishId == hasSetmealId) {
            throw new BusinessException("dishId 和 setmealId 必须且只能传一个");
        }
    }

    private LambdaQueryWrapper<ShoppingCart> buildWrapper(ShoppingCartDTO dto, Long userId) {
        checkDTO(dto);

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);

        if (dto.getDishId() != null) {
            wrapper.eq(ShoppingCart::getDishId, dto.getDishId());
        }

        if (dto.getSetmealId() != null) {
            wrapper.eq(ShoppingCart::getSetmealId, dto.getSetmealId());
        }

        String flavor = StringUtils.hasText(dto.getDishFlavor()) ? dto.getDishFlavor().trim() : null;
        if (flavor == null) {
            wrapper.isNull(ShoppingCart::getDishFlavor);
        } else {
            wrapper.eq(ShoppingCart::getDishFlavor, flavor);
        }

        return wrapper;
    }

    private void fillCartItemInfo(ShoppingCart shoppingCart, ShoppingCartDTO dto) {
        if (dto.getDishId() != null) {
            Dish dish = dishMapper.selectById(dto.getDishId());
            if (dish == null) {
                throw new BusinessException("菜品不存在");
            }
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
            return;
        }

        Setmeal setmeal = setmealMapper.selectById(dto.getSetmealId());
        if (setmeal == null) {
            throw new BusinessException("套餐不存在");
        }
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setAmount(setmeal.getPrice());
    }

    @Transactional
    @Override
    public void deleteOne(ShoppingCartDTO dto) {

        LambdaQueryWrapper<ShoppingCart> wrapper = buildWrapper(dto, getUserId());

        ShoppingCart cart = shoppingCartMapper.selectOne(wrapper);

        if (cart == null) throw new BusinessException("购物车不存在该商品");

        Integer number = cart.getNumber() == null ? 1 : cart.getNumber();

        if (number > 1) {
            cart.setNumber(number - 1);
            shoppingCartMapper.updateById(cart);
        } else {
            shoppingCartMapper.deleteById(cart.getId());
        }

    }

    @Override
    public List<ShoppingCart> selectList() {
        return shoppingCartMapper.selectList(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, getUserId()));
    }

    @Transactional
    @Override
    public void add(ShoppingCartDTO dto) {
        LambdaQueryWrapper<ShoppingCart> wrapper = buildWrapper(dto, getUserId());

        ShoppingCart cart = shoppingCartMapper.selectOne(wrapper);

        if (cart != null) {
            Integer number = cart.getNumber() == null ? 0 : cart.getNumber();
            cart.setNumber(number + 1);

            fillCartItemInfo(cart, dto);

            shoppingCartMapper.updateById(cart);
            return;
        }

        String flavor = StringUtils.hasText(dto.getDishFlavor()) ? dto.getDishFlavor().trim() : null;

        ShoppingCart shoppingCart = new ShoppingCart()
                .setDishFlavor(flavor)
                .setDishId(dto.getDishId())
                .setSetmealId(dto.getSetmealId())
                .setUserId(getUserId())
                .setNumber(1);

        fillCartItemInfo(shoppingCart, dto);

        shoppingCartMapper.insert(shoppingCart);
    }

    @Transactional
    @Override
    public void clean() {
        shoppingCartMapper.delete(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, getUserId()));
    }
}
