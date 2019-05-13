package com.ljx.controller;

import com.ljx.domain.Employee;
import com.ljx.dto.ClaimVoucherInfo;
import com.ljx.global.Contant;
import com.ljx.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Autowired
    private ClaimVoucherService claimVoucherService;
    //去添加
    @RequestMapping("/to_add")
    public String toadd(Map<String,Object> map){
        map.put("items", Contant.getItems());
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }
    //添加
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info){
        //获取当前登录用户
        Employee employee = (Employee) session.getAttribute("employee");
        //设置报销单创建者的编号设置为当前登录用户的工号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.save(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }
    //保存完后跳到详情页面
    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItems(id));
        map.put("records",claimVoucherService.getRecords(id));
        return "claim_voucher_detail";

    }
    //获取个人报销单
    @RequestMapping("/self")
    public String self(HttpSession session,Map<String,Object> map){
        //获取当前登录用户
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherService.getForSelf(employee.getSn()));
        return "claim_voucher_self";

    }
    //获取待处理报销单
    @RequestMapping("/deal")
    public String deal(HttpSession session,Map<String,Object> map){
        //获取当前登录用户
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherService.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }
    //修改报销单
    //去更新
    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info =new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherService.get(id));
        info.setItems(claimVoucherService.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }
    //更新报销单
    @RequestMapping("/update")
    public String update(HttpSession session, ClaimVoucherInfo info){
        //获取当前登录用户
        Employee employee = (Employee)session.getAttribute("employee");
        //设置报销单创建者的编号设置为当前登录用户的工号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.update(info.getClaimVoucher(),info.getItems());
        //重定向到待处理报销单
        return "redirect:deal";
    }

}
