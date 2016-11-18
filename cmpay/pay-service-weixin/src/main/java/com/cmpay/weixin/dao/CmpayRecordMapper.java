package com.cmpay.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmpay.weixin.model.CmpayRecord;
import com.cmpay.weixin.model.CmpayRecordExample;

public interface CmpayRecordMapper {
    int countByExample(CmpayRecordExample example);

    int deleteByExample(CmpayRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayRecord record);

    int insertSelective(CmpayRecord record);

    List<CmpayRecord> selectByExample(CmpayRecordExample example);

    CmpayRecord selectByPrimaryKey(String id);
    CmpayRecord selectByOrderId(CmpayRecord record);
    CmpayRecord selectByCmpayOrderId(String orderId);


    int updateByExampleSelective(@Param("record") CmpayRecord record, @Param("example") CmpayRecordExample example);

    int updateByExample(@Param("record") CmpayRecord record, @Param("example") CmpayRecordExample example);

    int updateByPrimaryKeySelective(CmpayRecord record);

    int updateByPrimaryKey(CmpayRecord record);
}