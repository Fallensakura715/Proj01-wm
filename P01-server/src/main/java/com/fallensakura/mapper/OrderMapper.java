package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

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

    @Select("select * from `order` where status = #{status} and order_time = #{orderTime}")
    List<Order> getByStatusAndOrdertimeLT(@Param("status") Integer status,
                                          @Param("orderTime") LocalDateTime orderTime);
}
