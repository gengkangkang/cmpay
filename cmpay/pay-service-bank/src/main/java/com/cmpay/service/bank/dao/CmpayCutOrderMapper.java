package com.cmpay.service.bank.dao;

import com.cmpay.service.bank.model.CmpayCutOrder;
import com.cmpay.service.bank.model.CmpayCutOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayCutOrderMapper {
    int countByExample(CmpayCutOrderExample example);

    int deleteByExample(CmpayCutOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmpayCutOrder record);

    int insertSelective(CmpayCutOrder record);

    List<CmpayCutOrder> selectByExample(CmpayCutOrderExample example);

    CmpayCutOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmpayCutOrder record, @Param("example") CmpayCutOrderExample example);

    int updateByExample(@Param("record") CmpayCutOrder record, @Param("example") CmpayCutOrderExample example);

    int updateByPrimaryKeySelective(CmpayCutOrder record);

    int updateByPrimaryKey(CmpayCutOrder record);
}