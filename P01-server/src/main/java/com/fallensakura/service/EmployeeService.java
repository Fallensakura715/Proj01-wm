package com.fallensakura.service;

import com.fallensakura.p01pojo.dto.EmployeeLoginDTO;
import com.fallensakura.p01pojo.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
}
