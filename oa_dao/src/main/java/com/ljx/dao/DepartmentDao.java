package com.ljx.dao;

import com.ljx.domain.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("departmentDao")
public interface DepartmentDao {
    //增加
    void insert(Department department);
    //更新
    void update(Department department);
    //删除
    void delete(String sn);
    //查询一个部门
    Department select(String sn);
    //查询所有部门
    List<Department> selectAll();
}
