package com.hb.study.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@Repository
@ToString
@NoArgsConstructor
public class Employee implements Serializable {
	private Integer employeeId;

    private String employeeName;

    private String email;

    //1 male, 0 female
    private Integer gender;

    private Department department;

//  @DateTimeFormat(style = "yyyy-MM-dd HH:mm")
    private Date birth;

//    @TableField(exist = false)
//    private Integer departmentId;
}
