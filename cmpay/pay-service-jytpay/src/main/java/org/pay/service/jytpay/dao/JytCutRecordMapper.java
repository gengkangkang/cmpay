package org.pay.service.jytpay.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.pay.service.jytpay.model.JytCutRecord;
import org.pay.service.jytpay.model.JytCutRecordExample;

public interface JytCutRecordMapper {
    int countByExample(JytCutRecordExample example);

    int deleteByExample(JytCutRecordExample example);

    int deleteByPrimaryKey(String tranFlowid);

    int insert(JytCutRecord record);

    int insertSelective(JytCutRecord record);

    List<JytCutRecord> selectByExample(JytCutRecordExample example);

    JytCutRecord selectByPrimaryKey(String tranFlowid);

    int updateByExampleSelective(@Param("record") JytCutRecord record, @Param("example") JytCutRecordExample example);

    int updateByExample(@Param("record") JytCutRecord record, @Param("example") JytCutRecordExample example);

    int updateByPrimaryKeySelective(JytCutRecord record);

    int updateByPrimaryKey(JytCutRecord record);
}