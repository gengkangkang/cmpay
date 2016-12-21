package com.cmpay.service.chinapay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmpay.service.chinapay.model.CpCutOrder;
import com.cmpay.service.chinapay.model.CpCutOrderExample;

public interface CpCutOrderMapper {
    int countByExample(CpCutOrderExample example);

    int deleteByExample(CpCutOrderExample example);

    int deleteByPrimaryKey(String cutOrderNo);

    int insert(CpCutOrder record);

    int insertSelective(CpCutOrder record);

    List<CpCutOrder> selectByExample(CpCutOrderExample example);

    CpCutOrder selectByPrimaryKey(String cutOrderNo);

    int updateByExampleSelective(@Param("record") CpCutOrder record, @Param("example") CpCutOrderExample example);

    int updateByExample(@Param("record") CpCutOrder record, @Param("example") CpCutOrderExample example);

    int updateByPrimaryKeySelective(CpCutOrder record);

    int updateByPrimaryKey(CpCutOrder record);
}