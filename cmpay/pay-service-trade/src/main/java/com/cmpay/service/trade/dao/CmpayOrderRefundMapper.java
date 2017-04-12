package com.cmpay.service.trade.dao;

import com.cmpay.service.trade.model.CmpayOrderRefund;
import com.cmpay.service.trade.model.CmpayOrderRefundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayOrderRefundMapper {
    int countByExample(CmpayOrderRefundExample example);

    int deleteByExample(CmpayOrderRefundExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayOrderRefund record);

    int insertSelective(CmpayOrderRefund record);

    List<CmpayOrderRefund> selectByExample(CmpayOrderRefundExample example);

    CmpayOrderRefund selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayOrderRefund record, @Param("example") CmpayOrderRefundExample example);

    int updateByExample(@Param("record") CmpayOrderRefund record, @Param("example") CmpayOrderRefundExample example);

    int updateByPrimaryKeySelective(CmpayOrderRefund record);

    int updateByPrimaryKey(CmpayOrderRefund record);
}