package com.cmpay.service.trade.dao;

import com.cmpay.service.trade.model.CmpayPayChannel;
import com.cmpay.service.trade.model.CmpayPayChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayPayChannelMapper {
    int countByExample(CmpayPayChannelExample example);

    int deleteByExample(CmpayPayChannelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayPayChannel record);

    int insertSelective(CmpayPayChannel record);

    List<CmpayPayChannel> selectByExample(CmpayPayChannelExample example);

    CmpayPayChannel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayPayChannel record, @Param("example") CmpayPayChannelExample example);

    int updateByExample(@Param("record") CmpayPayChannel record, @Param("example") CmpayPayChannelExample example);

    int updateByPrimaryKeySelective(CmpayPayChannel record);

    int updateByPrimaryKey(CmpayPayChannel record);
}