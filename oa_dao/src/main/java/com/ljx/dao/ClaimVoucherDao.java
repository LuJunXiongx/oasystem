package com.ljx.dao;

import com.ljx.domain.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
    //增加报销单
    void insert(ClaimVoucher claimVoucher);
    //更新报销单
    void update(ClaimVoucher claimVoucher);
    //删除报销单
    void delete(int id);
    //根据编号查找报销单
    ClaimVoucher select(int id);
    //根据创造者编号查找报销单
    List<ClaimVoucher> selectByCreateSn(String csn);
    //查询某个人能处理的报销单
    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
