package com.ljx.controller;


import com.ljx.domain.Employee;
import com.ljx.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller("globalController")
public class GlobalController {
    @Autowired
    private GlobalService globalService;

    /**
     * 去登陆
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     * @param session
     * @param sn
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password){
        Employee employee = globalService.login(sn,password);
        if (employee == null) {
            return "redirect:to_login";
        }
        session.setAttribute("employee",employee);
        return "redirect:self";
    }

    /**
     * 个人信息页面
     * @return
     */
    @RequestMapping("/self")
    public String self(){
        return  "self";
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    /**
     * 去改密码
     * @return
     */
    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    /**
     * 改密码
     * @param session
     * @param old
     * @param new1
     * @param new2
     * @return
     */
    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1 ,@RequestParam String new2){
        Employee employee = (Employee)session.getAttribute("employee");
        if(employee.getPassword().equals(old)){
            if(new1.equals(new2)){
                employee.setPassword(new1);
                globalService.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }

}
