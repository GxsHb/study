package com.hb.study.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Repository
@NoArgsConstructor
public class Department implements Serializable {

	private Integer departmentId;

	private String departmentName;

	private List<Employee> employeeList;

}
