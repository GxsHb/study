package com.hb.study.service;

import com.hb.study.dao.Employee;
import com.hb.study.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/03 11:56
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int addEmployee(Employee employee) {
       return employeeMapper.addEmployee(employee);
    }

    @Override
    public Employee queryEmployeeById(Integer employeeId) {
           return employeeMapper.queryEmployeeById(employeeId);
    }

    @Override
    public int deleteEmployeeById(Integer employeeId) {
        return employeeMapper.deleteEmployeeById(employeeId);
    }

    @Override
    public int editEmployee(Employee employee) {
        return employeeMapper.editEmployee(employee);
    }

    @Override
    @CachePut(value = "employeeList", key = "customKey")
    public List<Employee> queryAllEmployee() {
        return employeeMapper.queryAllEmployee();

    }
}
