package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyOrderRefund;
import com.cmpay.service.quartz.model.CmapyOrderRefundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyOrderRefundMapper {
    int countByExample(CmapyOrderRefundExample example);

    int deleteByExample(CmapyOrderRefundExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmapyOrderRefund record);

    int insertSelective(CmapyOrderRefund record);

    List<CmapyOrderRefund> selectByExample(CmapyOrderRefundExample example);

    CmapyOrderRefund selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmapyOrderRefund record, @Param("example") CmapyOrderRefundExample example);

    int updateByExample(@Param("record") CmapyOrderRefund record, @Param("example") CmapyOrderRefundExample example);

    int updateByPrimaryKeySelective(CmapyOrderRefund record);

    int updateByPrimaryKey(CmapyOrderRefund record);
}