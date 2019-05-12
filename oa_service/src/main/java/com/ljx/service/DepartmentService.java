package com.ljx.service;

import com.ljx.domain.Department;

import java.util.List;

public interface DepartmentService {
    //增加
    void insert(Department department);
    //更新
    void update(Department department);
    //删除
    void delete(String sn);
    //查询
    Department select(String sn);
    //查询所有
    List<Department> selectAll();
}
