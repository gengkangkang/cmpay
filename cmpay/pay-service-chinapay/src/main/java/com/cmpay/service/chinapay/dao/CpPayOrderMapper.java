package com.cmpay.service.chinapay.dao;

import com.cmpay.service.chinapay.model.CpPayOrder;
import com.cmpay.service.chinapay.model.CpPayOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPayOrderMapper {
    int countByExample(CpPayOrderExample example);

    int deleteByExample(CpPayOrderExample example);

    int deleteByPrimaryKey(String payOrderNo);

    int insert(CpPayOrder record);

    int insertSelective(CpPayOrder record);

    List<CpPayOrder> selectByExample(CpPayOrderExample example);

    CpPayOrder selectByPrimaryKey(String payOrderNo);

    int updateByExampleSelective(@Param("record") CpPayOrder record, @Param("example") CpPayOrderExample example);

    int updateByExample(@Param("record") CpPayOrder record, @Param("example") CpPayOrderExample example);

    int updateByPrimaryKeySelective(CpPayOrder record);

    int updateByPrimaryKey(CpPayOrder record);
}