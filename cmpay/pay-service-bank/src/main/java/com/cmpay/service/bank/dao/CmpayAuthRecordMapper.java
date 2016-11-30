package com.cmpay.service.bank.dao;

import com.cmpay.service.bank.model.CmpayAuthRecord;
import com.cmpay.service.bank.model.CmpayAuthRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayAuthRecordMapper {
    int countByExample(CmpayAuthRecordExample example);

    int deleteByExample(CmpayAuthRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayAuthRecord record);

    int insertSelective(CmpayAuthRecord record);

    List<CmpayAuthRecord> selectByExample(CmpayAuthRecordExample example);

    CmpayAuthRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayAuthRecord record, @Param("example") CmpayAuthRecordExample example);

    int updateByExample(@Param("record") CmpayAuthRecord record, @Param("example") CmpayAuthRecordExample example);

    int updateByPrimaryKeySelective(CmpayAuthRecord record);

    int updateByPrimaryKey(CmpayAuthRecord record);
}