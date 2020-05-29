package com.hb.study.service;

import com.hb.study.dao.Employee;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/03 11:54
 */
public interface EmployeeService {
    int addEmployee(Employee employee);

    Employee queryEmployeeById(Integer employeeId);

    int deleteEmployeeById(Integer employeeId);

    int editEmployee(Employee employee);

    List<Employee> queryAllEmployee();
}
