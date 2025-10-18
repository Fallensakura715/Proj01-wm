package com.fallensakura.controller;

import com.fallensakura.dto.EditPasswordDTO;
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
@Tag(name = "员工管理")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(description = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {

        Employee employee = employeeService.login(employeeLoginDTO);

        String token = JwtUtil.generateToken(employee.getId());
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .password(employee.getPassword())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    @PostMapping("/logout")
    @Operation(description = "员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    @PutMapping("/editPassword")
    @Operation(description = "修改员工密码")
    public Result<String> editPasssword(@RequestBody EditPasswordDTO editPasswordDTO) {
        employeeService.editPassword(editPasswordDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @Operation(description = "启用、禁用员工账号")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        employeeService.updateStatus(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(description = "员工分页查询")
    public Result<PageResult<Employee>> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult<Employee> pageQuery = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageQuery);
    }
}
