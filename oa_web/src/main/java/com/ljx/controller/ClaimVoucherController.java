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
        Employee employee = (Employee) session.getAttribute("employee");
        //设置报销单创建者的编号设置为当前登录用户的工号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.save(info.getClaimVoucher(),info.getItems());
        return "redirect:detail?id="+info.getClaimVoucher().getId();
    }
    //保存完后跳到详情页面
    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItems(id));
        map.put("records",claimVoucherService.getRecords(id));
        return "claim_voucher_detail";

    }
}
