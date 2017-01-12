package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapySettTotal;
import com.cmpay.service.quartz.model.CmapySettTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapySettTotalMapper {
    int countByExample(CmapySettTotalExample example);

    int deleteByExample(CmapySettTotalExample example);

    int deleteByPrimaryKey(String batchId);

    int insert(CmapySettTotal record);

    int insertSelective(CmapySettTotal record);

    List<CmapySettTotal> selectByExample(CmapySettTotalExample example);

    CmapySettTotal selectByPrimaryKey(String batchId);

    int updateByExampleSelective(@Param("record") CmapySettTotal record, @Param("example") CmapySettTotalExample example);

    int updateByExample(@Param("record") CmapySettTotal record, @Param("example") CmapySettTotalExample example);

    int updateByPrimaryKeySelective(CmapySettTotal record);

    int updateByPrimaryKey(CmapySettTotal record);
}