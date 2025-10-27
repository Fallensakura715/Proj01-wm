package com.fallensakura.controller.admin;

import com.fallensakura.context.BaseContext;
import com.fallensakura.properties.JwtProperties;
import com.fallensakura.dto.EditPasswordDTO;
import com.fallensakura.dto.EmployeeDTO;
import com.fallensakura.dto.EmployeeLoginDTO;
import com.fallensakura.dto.EmployeePageQueryDTO;
import com.fallensakura.entity.Employee;
import com.fallensakura.result.PageResult;
import com.fallensakura.result.Result;
import com.fallensakura.service.EmployeeService;
import com.fallensakura.util.JwtUtil;
import com.fallensakura.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工接口")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        Employee employee = employeeService.login(employeeLoginDTO);
        String token = JwtUtil.generateToken(
                employee.getId(),
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getExpirationTime()
        );
        EmployeeLoginVO vo = EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        return Result.success(vo);
    }

    @PostMapping("/logout")
    @Operation(summary = "员工登出")
    public Result<?> logout() {
        BaseContext.clear();
        return Result.success();
    }

    @PutMapping("/editPassword")
    @Operation(summary = "修改员工密码")
    public Result<?> editPassword(@RequestBody EditPasswordDTO editPasswordDTO) {
        employeeService.editPassword(editPasswordDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "启用、禁用员工账号")
    public Result<?> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        employeeService.updateStatus(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult<Employee>> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult<Employee> page = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/")
    @Operation(summary = "新增员工")
    public Result<?> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询员工")
    public Result<Employee> selectById(@PathVariable Long id) {
        return Result.success(employeeService.selectById(id));
    }

    @PutMapping("/")
    @Operation(summary = "编辑员工信息")
    public Result<?> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return Result.success();
    }
}