package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fallensakura.constant.PasswordConstant;
import com.fallensakura.constant.StatusConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.dto.EditPasswordDTO;
import com.fallensakura.dto.EmployeeDTO;
import com.fallensakura.dto.EmployeeLoginDTO;
import com.fallensakura.dto.EmployeePageQueryDTO;
import com.fallensakura.entity.Employee;
import com.fallensakura.exception.AccountLockedException;
import com.fallensakura.exception.AccountNotFoundException;
import com.fallensakura.exception.PasswordErrorException;
import com.fallensakura.mapper.EmployeeMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.EmployeeService;
import com.fallensakura.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * <p>
 *  Employee服务实现类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = getEmployeeByUsername(username);

        if (!PasswordUtil.matches(password, employee.getPassword())) {
            throw new PasswordErrorException();
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException();
        }

        return employee;
    }

    @Transactional
    @Override
    public void editPassword(EditPasswordDTO editPasswordDTO) {
        String oldPassword = editPasswordDTO.getOldPassword();
        String newPassword = editPasswordDTO.getNewPassword();

        Employee employee = getEmployeeById(editPasswordDTO.getEmployeeId());

        if (!PasswordUtil.matches(oldPassword, employee.getPassword())) {
            log.info("Wrong password: {}", oldPassword);
            log.info("::{}", DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
            throw new PasswordErrorException();
        }

        employee.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        employeeMapper.updateById(employee);
    }

    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        if (!status.equals(StatusConstant.ENABLE) && !status.equals(StatusConstant.DISABLE)) {
            throw new IllegalArgumentException("状态值错误");
        }

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.updateById(employee);
    }

    @Override
    public PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        Page<Employee> page = new Page<>(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(employeePageQueryDTO.getName()),
                Employee::getName, employeePageQueryDTO.getName())
                .orderByDesc(Employee::getCreateTime);

        Page<Employee> employeePage = employeeMapper.selectPage(page, wrapper);

        return new PageResult<>(employeePage.getTotal(), employeePage.getRecords());
    }

    @Transactional
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }

    @Override
    public Employee selectById(Long id) {
        Employee employee = getEmployeeById(id);
        employee.setPassword("****");
        return employee;
    }

    @Transactional
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = selectById(employeeDTO.getId());
        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setUpdateTime(LocalDateTime.now());

        employeeMapper.updateById(employee);
    }

    private Employee getEmployeeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        Employee employee = employeeMapper.selectById(id);
        if (employee == null) {
            throw new AccountNotFoundException();
        }
        return employee;
    }

    private Employee getEmployeeByUsername(String username) {
        Employee employee = employeeMapper.selectByUsername(username);
        if (employee == null) {
            throw new AccountNotFoundException();
        }
        return employee;
    }

}
