package com.cmpay.gateway.dao;

import java.util.List;

import com.cmpay.gateway.model.CmpayCardBin;
import com.cmpay.gateway.model.CmpayCardBinKey;

public interface CmpayCardBinMapper {

	List<CmpayCardBin> getCardBinList();

    int deleteByPrimaryKey(CmpayCardBinKey key);

    int insert(CmpayCardBin record);

    int insertSelective(CmpayCardBin record);

    CmpayCardBin selectByPrimaryKey(CmpayCardBinKey key);

    int updateByPrimaryKeySelective(CmpayCardBin record);

    int updateByPrimaryKey(CmpayCardBin record);
}