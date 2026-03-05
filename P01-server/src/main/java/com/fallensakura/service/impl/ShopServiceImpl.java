package com.fallensakura.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fallensakura.service.ShopService;

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
public class ShopServiceImpl implements ShopService {

    public static final String STATUS = "SHOP_STATUS";
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Integer getStatus() {
        return (Integer) redisTemplate.opsForValue().get(STATUS);
    }

    @Override
    public void setStatus(Integer status) {
        redisTemplate.opsForValue().set(STATUS, status);
    }
}
