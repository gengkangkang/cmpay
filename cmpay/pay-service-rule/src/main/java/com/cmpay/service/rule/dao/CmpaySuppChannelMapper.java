package com.cmpay.service.rule.dao;

import com.cmpay.service.rule.model.CmpaySuppChannel;
import com.cmpay.service.rule.model.CmpaySuppChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpaySuppChannelMapper {
    int countByExample(CmpaySuppChannelExample example);

    int deleteByExample(CmpaySuppChannelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpaySuppChannel record);

    int insertSelective(CmpaySuppChannel record);

    List<CmpaySuppChannel> selectByExample(CmpaySuppChannelExample example);

    CmpaySuppChannel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpaySuppChannel record, @Param("example") CmpaySuppChannelExample example);

    int updateByExample(@Param("record") CmpaySuppChannel record, @Param("example") CmpaySuppChannelExample example);

    int updateByPrimaryKeySelective(CmpaySuppChannel record);

    int updateByPrimaryKey(CmpaySuppChannel record);
}