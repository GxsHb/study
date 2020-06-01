package com.hb.study.mapper;

import com.hb.study.dao.Employee;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/03 11:48
 */
public interface EmployeeMapper {

       int addEmployee(Employee employee);

       Employee queryEmployeeById(Integer employeeId);

       int deleteEmployeeById(Integer employeeId);

       int editEmployee(Employee employee);

       List<Employee> queryAllEmployee();
}
