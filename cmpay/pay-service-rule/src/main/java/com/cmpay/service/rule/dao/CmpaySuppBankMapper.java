package com.cmpay.service.rule.dao;

import com.cmpay.service.rule.model.CmpaySuppBank;
import com.cmpay.service.rule.model.CmpaySuppBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpaySuppBankMapper {
    int countByExample(CmpaySuppBankExample example);

    int deleteByExample(CmpaySuppBankExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpaySuppBank record);

    int insertSelective(CmpaySuppBank record);

    List<CmpaySuppBank> selectByExample(CmpaySuppBankExample example);

    CmpaySuppBank selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpaySuppBank record, @Param("example") CmpaySuppBankExample example);

    int updateByExample(@Param("record") CmpaySuppBank record, @Param("example") CmpaySuppBankExample example);

    int updateByPrimaryKeySelective(CmpaySuppBank record);

    int updateByPrimaryKey(CmpaySuppBank record);
}