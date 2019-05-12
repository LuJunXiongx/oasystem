package com.ljx.service.impl;

import com.ljx.dao.ClaimVoucherDao;
import com.ljx.dao.ClaimVoucherItemDao;
import com.ljx.dao.DealRecordDao;
import com.ljx.domain.ClaimVoucher;
import com.ljx.domain.ClaimVoucherItem;
import com.ljx.domain.DealRecord;
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
}
