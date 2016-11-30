package com.cmpay.service.bank.dao;

import com.cmpay.service.bank.model.CmpayChannelConfig;
import com.cmpay.service.bank.model.CmpayChannelConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayChannelConfigMapper {
    int countByExample(CmpayChannelConfigExample example);

    int deleteByExample(CmpayChannelConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayChannelConfig record);

    int insertSelective(CmpayChannelConfig record);

    List<CmpayChannelConfig> selectByExample(CmpayChannelConfigExample example);

    CmpayChannelConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayChannelConfig record, @Param("example") CmpayChannelConfigExample example);

    int updateByExample(@Param("record") CmpayChannelConfig record, @Param("example") CmpayChannelConfigExample example);

    int updateByPrimaryKeySelective(CmpayChannelConfig record);

    int updateByPrimaryKey(CmpayChannelConfig record);
}