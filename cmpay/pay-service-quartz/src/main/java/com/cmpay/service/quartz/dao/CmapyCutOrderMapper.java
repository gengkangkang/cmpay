package com.cmpay.service.quartz.dao;

import com.cmpay.service.quartz.model.CmapyCutOrder;
import com.cmpay.service.quartz.model.CmapyCutOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmapyCutOrderMapper {
    int countByExample(CmapyCutOrderExample example);

    int deleteByExample(CmapyCutOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(CmapyCutOrder record);

    int insertSelective(CmapyCutOrder record);

    List<CmapyCutOrder> selectByExample(CmapyCutOrderExample example);

    CmapyCutOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CmapyCutOrder record, @Param("example") CmapyCutOrderExample example);

    int updateByExample(@Param("record") CmapyCutOrder record, @Param("example") CmapyCutOrderExample example);

    int updateByPrimaryKeySelective(CmapyCutOrder record);

    int updateByPrimaryKey(CmapyCutOrder record);
}