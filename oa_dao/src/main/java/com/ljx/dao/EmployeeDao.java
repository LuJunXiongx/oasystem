package com.ljx.dao;

import com.ljx.domain.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {
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
    //根据部门编号或职位获取待处理人
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
}
