package com.fallensakura.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.annotation.AutoFill;
import com.fallensakura.dto.CategoryDTO;
import com.fallensakura.dto.CategoryPageQueryDTO;
import com.fallensakura.entity.Category;
import com.fallensakura.entity.Setmeal;
import com.fallensakura.enumeration.OperationType;
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
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select * from category where type = #{type}")
    List<Category> selectByType(Integer type);

    @AutoFill(OperationType.UPDATE)
    @Override
    int updateById(Category entity);

    @AutoFill(OperationType.INSERT)
    @Override
    int insert(Category entity);

    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    Page<Category> selectPage(Page<Category> page, @Param("dto") CategoryPageQueryDTO dto);
}
