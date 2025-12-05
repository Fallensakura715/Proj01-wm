package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    Double sumTurnover(@Param("beginTime") LocalDateTime beginTime,
                       @Param("endTime") LocalDateTime endTime);

    Integer countValidOrders(@Param("beginTime") LocalDateTime beginTime,
                             @Param("endTime") LocalDateTime endTime);

    Integer countTotalOrders(@Param("beginTime") LocalDateTime beginTime,
                             @Param("endTime") LocalDateTime endTime);
}
