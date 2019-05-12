package com.ljx.service.impl;

import com.ljx.dao.DepartmentDao;
import com.ljx.domain.Department;
import com.ljx.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentBiz")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public void insert(Department department) {
        //添加
        departmentDao.insert(department);
    }

    public void update(Department department) {
        //更新
        departmentDao.update(department);

    }

    public void delete(String sn) {
        //根据sn删除
        departmentDao.delete(sn);

    }

    public Department select(String sn) {
        return departmentDao.select(sn);
    }

    public List<Department> selectAll() {
        //查所有
        return departmentDao.selectAll();
    }
}
