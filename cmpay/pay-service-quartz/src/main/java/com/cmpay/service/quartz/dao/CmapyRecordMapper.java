package com.cmpay.service.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmpay.service.quartz.model.CmapyRecord;
import com.cmpay.service.quartz.model.CmapyRecordExample;

public interface CmapyRecordMapper {
    int countByExample(CmapyRecordExample example);

    int deleteByExample(CmapyRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmapyRecord record);

    int insertSelective(CmapyRecord record);

    List<CmapyRecord> selectByExample(CmapyRecordExample example);

    CmapyRecord selectByPrimaryKey(String id);

    CmapyRecord selectByOrderId(String orderId);

    int updateByExampleSelective(@Param("record") CmapyRecord record, @Param("example") CmapyRecordExample example);

    int updateByExample(@Param("record") CmapyRecord record, @Param("example") CmapyRecordExample example);

    int updateByPrimaryKeySelective(CmapyRecord record);

    int updateByPrimaryKey(CmapyRecord record);

    int updateExpireOrder();
}