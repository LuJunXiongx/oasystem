package com.ljx.service;

import com.ljx.domain.Employee;

import java.util.List;

public interface EmployeeService {
    //增加
    void insert(Employee employee);
    //更新
    void update(Employee employee);
    //删除
    void delete(String sn);
    //查询
    Employee select(String sn);
    //查询所有
    List<Employee> selectAll();
}
