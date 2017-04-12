package com.cmpay.service.quartz.model;

import java.math.BigDecimal;
import java.util.Date;

public class CmapyOrderRefund {
    private String id;

    private String inchannel;

    private String merNo;

    private String userId;

    private BigDecimal refundAmt;

    private String payChannel;

    private String origOrderid;

    private String refundStatus;

    private String refundType;

    private String respCode;

    private String respMsg;

    private String thirdRespCode;

    private String thirdRespMsg;

    private Date createTime;

    private Date modifyTime;

    private Integer version;

    private String remark;

    private String field1;

    private String field2;

    private String field3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInchannel() {
        return inchannel;
    }

    public void setInchannel(String inchannel) {
        this.inchannel = inchannel == null ? null : inchannel.trim();
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo == null ? null : merNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    public String getOrigOrderid() {
        return origOrderid;
    }

    public void setOrigOrderid(String origOrderid) {
        this.origOrderid = origOrderid == null ? null : origOrderid.trim();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType == null ? null : refundType.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg == null ? null : respMsg.trim();
    }

    public String getThirdRespCode() {
        return thirdRespCode;
    }

    public void setThirdRespCode(String thirdRespCode) {
        this.thirdRespCode = thirdRespCode == null ? null : thirdRespCode.trim();
    }

    public String getThirdRespMsg() {
        return thirdRespMsg;
    }

    public void setThirdRespMsg(String thirdRespMsg) {
        this.thirdRespMsg = thirdRespMsg == null ? null : thirdRespMsg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

	@Override
	public String toString() {
		return "CmapyOrderRefund [id=" + id + ", inchannel=" + inchannel + ", merNo=" + merNo + ", userId=" + userId
				+ ", refundAmt=" + refundAmt + ", payChannel=" + payChannel + ", origOrderid=" + origOrderid
				+ ", refundStatus=" + refundStatus + ", refundType=" + refundType + ", respCode=" + respCode
				+ ", respMsg=" + respMsg + ", thirdRespCode=" + thirdRespCode + ", thirdRespMsg=" + thirdRespMsg
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", version=" + version + ", remark="
				+ remark + ", field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
}