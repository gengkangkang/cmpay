package com.cmpay.service.trade.dao;

import com.cmpay.service.trade.model.CmpayCardBin;
import com.cmpay.service.trade.model.CmpayCardBinExample;
import com.cmpay.service.trade.model.CmpayCardBinKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmpayCardBinMapper {
    int countByExample(CmpayCardBinExample example);

    int deleteByExample(CmpayCardBinExample example);

    int deleteByPrimaryKey(CmpayCardBinKey key);

    int insert(CmpayCardBin record);

    int insertSelective(CmpayCardBin record);

    List<CmpayCardBin> selectByExample(CmpayCardBinExample example);

    CmpayCardBin selectByPrimaryKey(CmpayCardBinKey key);

    int updateByExampleSelective(@Param("record") CmpayCardBin record, @Param("example") CmpayCardBinExample example);

    int updateByExample(@Param("record") CmpayCardBin record, @Param("example") CmpayCardBinExample example);

    int updateByPrimaryKeySelective(CmpayCardBin record);

    int updateByPrimaryKey(CmpayCardBin record);
}