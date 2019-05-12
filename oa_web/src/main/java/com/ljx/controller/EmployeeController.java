package com.ljx.controller;

import com.ljx.domain.Department;
import com.ljx.domain.Employee;
import com.ljx.global.Contant;
import com.ljx.service.DepartmentService;
import com.ljx.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("EmployeeController")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 查询
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeService.selectAll());
        return "employee_list";
    }

    /**
     * 当按+按钮时跳转到添加页面
     * @param map
     * @return
     */
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("employee",new Employee());
        map.put("dlist",departmentService.selectAll());
        map.put("plist", Contant.getPosts());
        return "employee_add";
    }

    /**
     * 添加功能
     * @param employee
     * @return
     */
    @RequestMapping("/add")
    public String add(Employee employee){
        employeeService.insert(employee);
        return "redirect:list";
    }

    /**
     * 根据sn查询到要修改人的信息
     * @param sn
     * @param map
     * @return
     */
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",employeeService.select(sn));
        map.put("dlist",departmentService.selectAll());
        map.put("plist", Contant.getPosts());
        return "employee_update";
    }

    /**
     * 修改信息，然后跳回列表页面
     * @param employee
     * @return
     */
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeService.update(employee);
        return "redirect:list";
    }

    /**
     * 根据sn删除
     * @param sn
     * @return
     */
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeService.delete(sn);
        return "redirect:list";
    }


}
