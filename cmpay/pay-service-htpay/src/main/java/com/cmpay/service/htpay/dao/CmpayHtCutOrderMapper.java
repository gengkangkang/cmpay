package com.cmpay.service.htpay.dao;

import com.cmpay.service.htpay.model.CmpayHtCutOrder;
import com.cmpay.service.htpay.model.CmpayHtCutOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayHtCutOrderMapper {
    int countByExample(CmpayHtCutOrderExample example);

    int deleteByExample(CmpayHtCutOrderExample example);

    int deleteByPrimaryKey(String orderNo);

    int insert(CmpayHtCutOrder record);

    int insertSelective(CmpayHtCutOrder record);

    List<CmpayHtCutOrder> selectByExample(CmpayHtCutOrderExample example);

    CmpayHtCutOrder selectByPrimaryKey(String orderNo);

    int updateByExampleSelective(@Param("record") CmpayHtCutOrder record, @Param("example") CmpayHtCutOrderExample example);

    int updateByExample(@Param("record") CmpayHtCutOrder record, @Param("example") CmpayHtCutOrderExample example);

    int updateByPrimaryKeySelective(CmpayHtCutOrder record);

    int updateByPrimaryKey(CmpayHtCutOrder record);
}