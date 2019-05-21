package com.ljx.service.impl;

import com.ljx.dao.ClaimVoucherDao;
import com.ljx.dao.ClaimVoucherItemDao;
import com.ljx.dao.DealRecordDao;
import com.ljx.dao.EmployeeDao;
import com.ljx.domain.ClaimVoucher;
import com.ljx.domain.ClaimVoucherItem;
import com.ljx.domain.DealRecord;
import com.ljx.domain.Employee;
import com.ljx.global.Contant;
import com.ljx.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("cliamVoucherService")
public class ClaimVoucherServiceImpl implements ClaimVoucherService {
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;
    @Autowired
    private EmployeeDao employeeDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //设置时间
        claimVoucher.setCreateTime(new Date());
        //把处理人设置为创建者
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置报销单状态
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        //保存报销单
        claimVoucherDao.insert(claimVoucher);
        for (ClaimVoucherItem item:items){
            //获取报销单编号
            item.setClaimVoucherId(claimVoucher.getId());
            //保存报销单条目
            claimVoucherItemDao.insert(item);
        }


    }
    //获取报销单
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }
    //获取报销单条目
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }
    //审核记录
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    //获取个人报销单
    public List<ClaimVoucher> getForSelf(String sn) {
        //创建者报销单
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getForDeal(String sn) {
        //获取待处理人的报销单编号
        return claimVoucherDao.selectByNextDealSn(sn);
    }
    //修改报销单
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //把处理人设置为创建者
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置报销单状态
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);
        //更新条目集合
        //获取当前数据库已有的条目
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for(ClaimVoucherItem old:olds){
            //判断是否存在
            boolean isHave=false;
            //迭代现有的
            for(ClaimVoucherItem item:items){
                if(item.getId()==old.getId()){
                    isHave=true;//代表存在
                    break;
                }
            }
            if(!isHave){//如果没找到
                claimVoucherItemDao.delete(old.getId());
            }
        }
        //修改已经有的条目
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            if(item.getId()>0){
                //存在的话没去更新
                claimVoucherItemDao.update(item);
            }else{
                //不存在
                claimVoucherItemDao.insert(item);
            }
        }

    }

    //提交报销单
    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());

        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);//报销单状态：已提交
        //待处理人是该员工同部门的部门经理
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);
        //记录保存
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        //处理人
        dealRecord.setDealSn(employee.getSn());
        //报销单编号
        dealRecord.setClaimVoucherId(id);
        //处理结果
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        //处理时间
        dealRecord.setDealTime(new Date());
        //备注
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());

        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){//审核通过
            if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){
                //不需要复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());
                //设置报销单处理结果
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{
                //需要复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getSn());
                //设置报销单处理结果
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){//审核不通过，打回
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);//打回
            //设置待处理人
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
            //处理结果
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){//拒绝
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);//终止
            //没有处理人
            claimVoucher.setNextDealSn(null);
            //处理结果
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){//打款
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);//已打款
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }


}
