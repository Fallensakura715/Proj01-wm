package com.fallensakura.service.impl;

import com.fallensakura.constant.StatusConstant;
import com.fallensakura.dto.EditPasswordDTO;
import com.fallensakura.dto.EmployeeLoginDTO;
import com.fallensakura.dto.EmployeePageQueryDTO;
import com.fallensakura.entity.Employee;
import com.fallensakura.exception.AccountLockedException;
import com.fallensakura.exception.AccountNotFoundException;
import com.fallensakura.exception.PasswordErrorException;
import com.fallensakura.mapper.EmployeeMapper;
import com.fallensakura.result.PageResult;
import com.fallensakura.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
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

        if (!password.equals(employee.getPassword())) {
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

        if (!oldPassword.equals(employee.getPassword())) {
            throw new PasswordErrorException();
        }

        employee.setPassword(newPassword);
        employeeMapper.updateById(employee);
    }

    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Employee employee = getEmployeeById(id);
        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException();
        }

        if (!status.equals(StatusConstant.ENABLE) && !status.equals(StatusConstant.DISABLE)) {
            throw new IllegalArgumentException("状态值错误");
        }

        employee.setStatus(status);
        employeeMapper.updateById(employee);
    }

    @Override
    public PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        return null;
    }

    private Employee getEmployeeById(Long id) {
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
