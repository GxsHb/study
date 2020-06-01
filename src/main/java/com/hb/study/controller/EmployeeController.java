package com.hb.study.controller;

import com.hb.study.dao.Employee;
import com.hb.study.service.DepartmentService;
import com.hb.study.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author huangbing
 * @date 2020/03/26 16:31
 */
@Controller
@RequestMapping("/st/cs")
public class EmployeeController {


       @Autowired
       private EmployeeService employeeService;

       @Autowired
       private DepartmentService departmentService;

       private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);


       @GetMapping("/emps")
       public String queryEmployees(Model model){
             model.addAttribute("employeeList", employeeService.queryAllEmployee());
             return "emps/list";
       }

       @GetMapping("/emp/query/{employeeId}")
       public String queryEmployeesById(Model model, @PathVariable("employeeId") Integer employeeId){
              if(!ObjectUtils.isEmpty(employeeId)){
                 employeeService.queryAllEmployee().forEach((employee)->{
                             if(employee.getEmployeeId().equals(employeeId)){
                                 model.addAttribute("employeeList", Arrays.asList(employeeService.queryEmployeeById(employee.getEmployeeId())));
                             }else {
                                 throw new RuntimeException("query employee not exit");
                             }
                  });
                  return "redirect:/st/cs/emps";
              }
                  //如果employeeId == null、默认查询列表,当数据库数据中数据多少,添加缓存技术,后期优化
                  model.addAttribute("employeeList", employeeService.queryAllEmployee());
                  return "redirect:/st/cs/emps";
       }

       @PostMapping("/emp/add")
       public String addEmployee(Model model, Employee employee){
              if(!ObjectUtils.isEmpty(employee)){
//                  employeeDao.save(employee);
                  employeeService.addEmployee(employee);
                  model.addAttribute("employeeList", employeeService.queryAllEmployee());
              }
              //redirect:表示重定向到一个地址     /代表当前项目路径等同于request.getContextPath()
              //forward:表示转发到一个地址
              //ps:这里如果直接写/emps模板引擎会到当前应用templates目录下去找这个路径.而不是直接调用/emps请求映射的方法
              // 原理可以参考ThymeleafProperties,ThymeleafViewResolver -> createView
              return "redirect:/st/cs/emps";
       }

       @GetMapping("/emp/addpage")
       public String goAddPage(Model model){
           model.addAttribute("departmentsList", departmentService.queryAllDepartment());
           return "emps/add";
       }

       @GetMapping("/emp/editpage/{employeeId}")
       public String goEditPage(@PathVariable("employeeId") Integer employeeId, Model model){
             model.addAttribute("emp",employeeService.queryEmployeeById(employeeId));
             model.addAttribute("departmentsList", departmentService.queryAllDepartment());
             return "emps/add";
       }

       @DeleteMapping("/emp/del/{employeeId}")
       public String deleteEmployeeById(@PathVariable("employeeId") Integer employeeId, Model model){
              if(!ObjectUtils.isEmpty(employeeId)){
                  employeeService.deleteEmployeeById(employeeId);
//                  Collection<Employee> employeeCollection = employeeDao.getAll();
//                  Iterator<Employee> iterator = employeeCollection.iterator();
//                  while(iterator.hasNext()){
//                      if(employeeId.equals(iterator.next().getId())){
//                          iterator.remove();
//                      }
//                  }

                  model.addAttribute("employeeList", employeeService.queryAllEmployee());
              }

              return "redirect:/st/cs/emps";
       }

       @PutMapping("emp/edit")
       public String editEmployee(Employee employee, Model model){
//                     课程41中演示没有调用save方法直接保存,员工id为null是因为修改页面没有id输入框。
//                     虽然点击编辑按钮跳转到修改页面的时候员工是有id值的, 但是点击发送修改请求的时候是
//                     不携带id参数的,导致springmvc在进行数据绑定的时候没有找到对应的参数与employee id
//                     属性映射。课程中是通过给修改页面增加了一个隐藏的id输入框
//                     <input type="hidden" name="id" th:if="${emp!=null}" th:value="${emp.id}">
//                     课程中是将addPage和editPage二合一了,虽然页面没有id输入框,但是save模拟了数据库主键自增
//                     ,所以在add的时候前端不需要id输入框也可以,但是edit的时候会导致id丢失
//                     employeeDao.save(employee);
                     employeeService.editEmployee(employee);
                     model.addAttribute("employeeList", employeeService.queryAllEmployee());
                     return "redirect:/st/cs/emps";
       }

}
