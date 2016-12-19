package com.cmpay.service.trade.dao;

import com.cmpay.service.trade.model.CmpayTradeRecord;
import com.cmpay.service.trade.model.CmpayTradeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayTradeRecordMapper {
    int countByExample(CmpayTradeRecordExample example);

    int deleteByExample(CmpayTradeRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayTradeRecord record);

    int insertSelective(CmpayTradeRecord record);

    List<CmpayTradeRecord> selectByExample(CmpayTradeRecordExample example);

    CmpayTradeRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayTradeRecord record, @Param("example") CmpayTradeRecordExample example);

    int updateByExample(@Param("record") CmpayTradeRecord record, @Param("example") CmpayTradeRecordExample example);

    int updateByPrimaryKeySelective(CmpayTradeRecord record);

    int updateByPrimaryKey(CmpayTradeRecord record);
}