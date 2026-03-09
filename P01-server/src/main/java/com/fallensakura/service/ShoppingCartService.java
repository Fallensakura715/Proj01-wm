package com.fallensakura.service;

import com.fallensakura.dto.ShoppingCartDTO;
import com.fallensakura.entity.ShoppingCart;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface ShoppingCartService {

    /**
     * 删除购物车中一个商品
     * @param dto ShoppingCartDTO
     */
    void deleteOne(ShoppingCartDTO dto);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> selectList();

    /**
     * 添加购物车
     * @param dto ShoppingCartDTO
     */
    void add(ShoppingCartDTO dto);

    /**
     * 清空购物车
     */
    void clean();
}
