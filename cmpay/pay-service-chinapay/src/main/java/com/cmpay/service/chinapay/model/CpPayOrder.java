package com.cmpay.service.chinapay.model;

import java.math.BigDecimal;
import java.util.Date;

public class CpPayOrder {
    private String payOrderNo;

    private BigDecimal amount;

    private String bankCode;

    private String cardName;

    private String cardNo;

    private String cardType;

    private String certId;

    private String certType;

    private String city;

    private String cpDate;

    private String cpSeqid;

    private Date createTime;

    private String curyId;

    private String custId;

    private String description;

    private String flag;

    private Long jpaVersion;

    private String merId;

    private Date procDate;

    private String prov;

    private String resCode;

    private String resStat;

    private String subBank;

    private String transDate;

    private String transId;

    private String transStatus;

    private String origOrderNo;

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId == null ? null : certId.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCpDate() {
        return cpDate;
    }

    public void setCpDate(String cpDate) {
        this.cpDate = cpDate == null ? null : cpDate.trim();
    }

    public String getCpSeqid() {
        return cpSeqid;
    }

    public void setCpSeqid(String cpSeqid) {
        this.cpSeqid = cpSeqid == null ? null : cpSeqid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCuryId() {
        return curyId;
    }

    public void setCuryId(String curyId) {
        this.curyId = curyId == null ? null : curyId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Long getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(Long jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Date getProcDate() {
        return procDate;
    }

    public void setProcDate(Date procDate) {
        this.procDate = procDate;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov == null ? null : prov.trim();
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResStat() {
        return resStat;
    }

    public void setResStat(String resStat) {
        this.resStat = resStat == null ? null : resStat.trim();
    }

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank == null ? null : subBank.trim();
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate == null ? null : transDate.trim();
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getOrigOrderNo() {
        return origOrderNo;
    }

    public void setOrigOrderNo(String origOrderNo) {
        this.origOrderNo = origOrderNo == null ? null : origOrderNo.trim();
    }
}