package com.cmpay.gateway.dao;

import com.cmpay.gateway.model.MerConfig;
import com.cmpay.gateway.model.MerConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerConfigMapper {
    int countByExample(MerConfigExample example);

    int deleteByExample(MerConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(MerConfig record);

    int insertSelective(MerConfig record);

    List<MerConfig> selectByExample(MerConfigExample example);

    MerConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MerConfig record, @Param("example") MerConfigExample example);

    int updateByExample(@Param("record") MerConfig record, @Param("example") MerConfigExample example);

    int updateByPrimaryKeySelective(MerConfig record);

    int updateByPrimaryKey(MerConfig record);
}