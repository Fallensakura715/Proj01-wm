package com.fallensakura.service;

import com.fallensakura.result.Result;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  店铺类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface ShopService {

    /**
     * 获取营业状态
     * @return status
     */
    Integer getStatus();

    /**
     * 设置营业状态
     * @param status
     */
    void setStatus(Integer status);
}
