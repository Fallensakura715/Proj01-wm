package com.fallensakura.controller.admin;

import com.fallensakura.constant.JwtClaimsConstant;
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
import com.fallensakura.utils.JwtUtils;
import com.fallensakura.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工接口")
@RequiredArgsConstructor
public class EmployeeController {

    private static final String ADMIN_LOGIN_TOKEN_PREFIX = "admin:login:token:";

    private final EmployeeService employeeService;
    private final JwtProperties jwtProperties;
    private final RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        Employee employee = employeeService.login(employeeLoginDTO);

        String token = JwtUtils.generateToken(
                Map.of(JwtClaimsConstant.EMPLOYEE_ID, employee.getId()),
                "employee_auth",
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getExpirationTime()
        );

        redisTemplate.opsForValue().set(
                ADMIN_LOGIN_TOKEN_PREFIX + employee.getId(),
                token,
                jwtProperties.getExpirationTime(),
                TimeUnit.MILLISECONDS
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
    public Result<String> logout() {
        Long employeeId = BaseContext.getCurrentId();
        if (employeeId != null) {
            redisTemplate.delete(ADMIN_LOGIN_TOKEN_PREFIX + employeeId);
        }
        return Result.success();
    }

    @PutMapping("/editPassword")
    @Operation(summary = "修改员工密码")
    public Result<String> editPassword(@RequestBody EditPasswordDTO editPasswordDTO) {
        employeeService.editPassword(editPasswordDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "启用、禁用员工账号")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        employeeService.updateStatus(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult<Employee>> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult<Employee> page = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(page);
    }

    @PostMapping
    @Operation(summary = "新增员工")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询员工")
    public Result<Employee> selectById(@PathVariable Long id) {
        return Result.success(employeeService.selectById(id));
    }

    @PutMapping
    @Operation(summary = "编辑员工信息")
    public Result<String> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return Result.success();
    }
}