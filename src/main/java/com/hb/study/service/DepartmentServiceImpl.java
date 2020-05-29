package com.hb.study.service;

import com.hb.study.dao.Department;
import com.hb.study.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/03 12:26
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department queryDepartmentById(Integer departmentId) {
        return departmentMapper.queryDepartmentById(departmentId);
    }

    @Override
    public List<Department> queryAllDepartment() {
        return departmentMapper.queryAllDepartment();
    }
}
