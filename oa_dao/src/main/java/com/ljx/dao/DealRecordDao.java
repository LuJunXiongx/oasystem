package com.ljx.dao;

import com.ljx.domain.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dealRecordDao")
public interface DealRecordDao {

    //增加处理记录
    void insert(DealRecord dealRecord);
    //根据报销单编号查询处理记录
    List<DealRecord> selectByClaimVoucher(int cvid);
}
