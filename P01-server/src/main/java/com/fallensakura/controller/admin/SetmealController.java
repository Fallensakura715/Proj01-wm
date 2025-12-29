package com.fallensakura.controller.admin;

import com.fallensakura.dto.SetmealDTO;
import com.fallensakura.dto.SetmealPageQueryDTO;
import com.fallensakura.result.PageResult;
import com.fallensakura.result.Result;
import com.fallensakura.service.SetmealService;
import com.fallensakura.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Tag(name = "套餐接口")
@RequiredArgsConstructor
public class SetmealController {

    private final SetmealService setmealService;

    @PutMapping
    @Operation(summary = "修改套餐")
    public Result<?> update(@RequestBody SetmealDTO dto) {
        setmealService.update(dto);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "套餐分页查询")
    public Result<PageResult<SetmealVO>> pageQuery(SetmealPageQueryDTO dto) {
        return Result.success(setmealService.pageQuery(dto));
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "套餐起售、停售")
    public Result<?> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        setmealService.updateStatus(status, id);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "批量删除套餐")
    public Result<?> deleteByIds(@RequestParam String ids) {
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "新增套餐")
    public Result<?> addSetmeal(@RequestBody SetmealDTO dto) {
        setmealService.addSetmeal(dto);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询套餐")
    public Result<SetmealVO> selectById(@PathVariable Long id) {
        return Result.success(setmealService.selectById(id));
    }
}
