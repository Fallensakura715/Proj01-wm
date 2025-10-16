package com.fallensakura.service;

import com.fallensakura.dto.EmployeeLoginDTO;
import com.fallensakura.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
}
