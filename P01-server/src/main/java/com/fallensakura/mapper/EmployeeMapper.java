package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fallensakura.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("select * from employee where username = #{username}")
    Employee selectByUsername(@Param("username") String username);
}
