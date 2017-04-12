package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyChannelConfig;
import com.cmpay.service.quartz.model.CmapyChannelConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyChannelConfigMapper {
    int countByExample(CmapyChannelConfigExample example);

    int deleteByExample(CmapyChannelConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmapyChannelConfig record);

    int insertSelective(CmapyChannelConfig record);

    List<CmapyChannelConfig> selectByExample(CmapyChannelConfigExample example);

    CmapyChannelConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmapyChannelConfig record, @Param("example") CmapyChannelConfigExample example);

    int updateByExample(@Param("record") CmapyChannelConfig record, @Param("example") CmapyChannelConfigExample example);

    int updateByPrimaryKeySelective(CmapyChannelConfig record);

    int updateByPrimaryKey(CmapyChannelConfig record);
}