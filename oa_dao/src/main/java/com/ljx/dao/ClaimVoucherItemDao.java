package com.ljx.dao;

import com.ljx.domain.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {
    //增加报销单条目
    void insert(ClaimVoucherItem claimVoucherItem);
    //更新报销单条目
    void update(ClaimVoucherItem claimVoucherItem);
    //根据编号删除报销单
    void delete(int id);
    //根据报销单条目查询报销单
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
