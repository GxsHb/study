package com.hb.study.service;

import com.hb.study.dao.Department;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/03 12:26
 */
public interface DepartmentService {

    Department queryDepartmentById(Integer departmentId);

    List<Department> queryAllDepartment();

}
