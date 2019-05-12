package com.ljx.service;

import com.ljx.domain.Employee;

public interface GlobalService {
    //登录
    Employee login(String sn, String password);
    //修改密码
    void changePassword(Employee employee);
}
