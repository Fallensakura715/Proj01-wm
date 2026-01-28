package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("select * from `order_detail` where order_id = #{orderId}")
    List<OrderDetail> selectByOrderId(@Param("orderId") Long orderId);
}
