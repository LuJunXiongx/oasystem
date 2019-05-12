package com.ljx.service;

import com.ljx.domain.ClaimVoucher;
import com.ljx.domain.ClaimVoucherItem;
import com.ljx.domain.DealRecord;

import java.util.List;

public interface ClaimVoucherService {
    //保存报销单
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    //获取报销单
    ClaimVoucher get(int id);
    //获取报销单条目
    List<ClaimVoucherItem> getItems(int cvid);
    //审核记录
    List<DealRecord> getRecords(int cvid);
}
