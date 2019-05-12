package com.ljx.controller;

import com.ljx.domain.Department;
import com.ljx.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",departmentService.selectAll());
        return "department_list";
    }

    /**
     * 当按+按钮时跳转到添加页面
     * @param map
     * @return
     */
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("department",new Department());
        return "department_add";
    }

    /**
     * 添加功能
     * @param department
     * @return
     */
    @RequestMapping("/add")
    public String add(Department department){
        departmentService.insert(department);
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
        map.put("department",departmentService.select(sn));
        return "department_update";
    }

    /**
     * 修改信息，然后跳回列表页面
     * @param department
     * @return
     */
    @RequestMapping("/update")
    public String update(Department department){
        departmentService.update(department);
        return "redirect:list";
    }

    /**
     * 根据sn删除
     * @param sn
     * @return
     */
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        departmentService.delete(sn);
        return "redirect:list";
    }


}
