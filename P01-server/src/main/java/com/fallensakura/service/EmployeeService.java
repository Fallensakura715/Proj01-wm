package com.fallensakura.service;

import com.fallensakura.dto.EditPasswordDTO;
import com.fallensakura.dto.EmployeeLoginDTO;
import com.fallensakura.dto.EmployeePageQueryDTO;
import com.fallensakura.entity.Employee;
import com.fallensakura.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 修改密码
     * @param editPasswordDTO
     */
    void editPassword(EditPasswordDTO editPasswordDTO);

    /**
     * 启用禁用员工账号
     * @param id 员工ID
     * @param status 状态，1为启用，0为禁用
     */
    void updateStatus(Integer status, Long id);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return pageResult
     */
    PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

}
