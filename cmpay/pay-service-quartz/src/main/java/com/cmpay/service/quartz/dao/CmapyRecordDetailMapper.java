package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyRecordDetail;
import com.cmpay.service.quartz.model.CmapyRecordDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyRecordDetailMapper {
    int countByExample(CmapyRecordDetailExample example);

    int deleteByExample(CmapyRecordDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmapyRecordDetail record);

    int insertSelective(CmapyRecordDetail record);

    List<CmapyRecordDetail> selectByExample(CmapyRecordDetailExample example);

    CmapyRecordDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmapyRecordDetail record, @Param("example") CmapyRecordDetailExample example);

    int updateByExample(@Param("record") CmapyRecordDetail record, @Param("example") CmapyRecordDetailExample example);

    int updateByPrimaryKeySelective(CmapyRecordDetail record);

    int updateByPrimaryKey(CmapyRecordDetail record);
}