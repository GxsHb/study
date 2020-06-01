package com.hb.study.mapper;

import com.hb.study.dao.Department;

import java.util.List;

/**
 * @author huangbing
 * @date 2020/04/02 13:13
 */
public interface DepartmentMapper {
         //基于注解开发mybatis
//       @Sql("sql语句")
//       @Options(useGeneratedKeys = true, keyProperty = "departmentId")
//       Department queryDepartmentById(Integer departmentId);
         Department queryDepartmentById(Integer departmentId);

         List<Department> queryAllDepartment();
}
