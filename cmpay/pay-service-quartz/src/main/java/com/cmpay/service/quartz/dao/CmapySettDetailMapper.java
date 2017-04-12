package com.cmpay.service.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmpay.service.quartz.model.CmapySettDetail;
import com.cmpay.service.quartz.model.CmapySettDetailExample;
import com.cmpay.service.quartz.model.SettTotalBean;

public interface CmapySettDetailMapper {
    int countByExample(CmapySettDetailExample example);

    int deleteByExample(CmapySettDetailExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(CmapySettDetail record);

    int insertSelective(CmapySettDetail record);

    List<CmapySettDetail> selectByExample(CmapySettDetailExample example);

    CmapySettDetail selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") CmapySettDetail record, @Param("example") CmapySettDetailExample example);

    int updateByExample(@Param("record") CmapySettDetail record, @Param("example") CmapySettDetailExample example);

    int updateByPrimaryKeySelective(CmapySettDetail record);

    int updateByPrimaryKey(CmapySettDetail record);

    //将清算数据移入结算表
    int payorderToSettDetail();

    //查询结算批次信息
    List<SettTotalBean> getDailySettTotal();
}