package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    Integer countByCreateTime(@Param("beginTime") LocalDateTime beginTime,
                              @Param("endTime") LocalDateTime endTime);
}
