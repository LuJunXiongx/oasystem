package com.ljx.service.impl;

import com.ljx.dao.EmployeeDao;
import com.ljx.domain.Employee;
import com.ljx.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("employeeService")
public class EmployeeServiceImpl  implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    public void insert(Employee employee) {
        employee.setPassword("000000");
        employeeDao.insert(employee);

    }

    public void update(Employee employee) {
        employeeDao.update(employee);

    }

    public void delete(String sn) {
        employeeDao.delete(sn);

    }

    public Employee select(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> selectAll() {
        return employeeDao.selectAll();
    }
}
