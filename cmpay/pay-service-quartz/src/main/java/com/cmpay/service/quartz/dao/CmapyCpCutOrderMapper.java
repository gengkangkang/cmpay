package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyCpCutOrder;
import com.cmpay.service.quartz.model.CmapyCpCutOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyCpCutOrderMapper {
    int countByExample(CmapyCpCutOrderExample example);

    int deleteByExample(CmapyCpCutOrderExample example);

    int deleteByPrimaryKey(String cutOrderNo);

    int insert(CmapyCpCutOrder record);

    int insertSelective(CmapyCpCutOrder record);

    List<CmapyCpCutOrder> selectByExample(CmapyCpCutOrderExample example);

    CmapyCpCutOrder selectByPrimaryKey(String cutOrderNo);

    int updateByExampleSelective(@Param("record") CmapyCpCutOrder record, @Param("example") CmapyCpCutOrderExample example);

    int updateByExample(@Param("record") CmapyCpCutOrder record, @Param("example") CmapyCpCutOrderExample example);

    int updateByPrimaryKeySelective(CmapyCpCutOrder record);

    int updateByPrimaryKey(CmapyCpCutOrder record);
}