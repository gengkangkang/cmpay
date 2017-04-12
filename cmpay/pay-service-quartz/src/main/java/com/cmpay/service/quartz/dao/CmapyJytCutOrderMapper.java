package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyJytCutOrder;
import com.cmpay.service.quartz.model.CmapyJytCutOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyJytCutOrderMapper {
    int countByExample(CmapyJytCutOrderExample example);

    int deleteByExample(CmapyJytCutOrderExample example);

    int deleteByPrimaryKey(String tranFlowid);

    int insert(CmapyJytCutOrder record);

    int insertSelective(CmapyJytCutOrder record);

    List<CmapyJytCutOrder> selectByExample(CmapyJytCutOrderExample example);

    CmapyJytCutOrder selectByPrimaryKey(String tranFlowid);

    int updateByExampleSelective(@Param("record") CmapyJytCutOrder record, @Param("example") CmapyJytCutOrderExample example);

    int updateByExample(@Param("record") CmapyJytCutOrder record, @Param("example") CmapyJytCutOrderExample example);

    int updateByPrimaryKeySelective(CmapyJytCutOrder record);

    int updateByPrimaryKey(CmapyJytCutOrder record);
}