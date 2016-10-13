package com.cmpay.boss.entity.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SYSFUNCVO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_ID
     *
     * @mbggenerated
     */
    private String     funcId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_NAME
     *
     * @mbggenerated
     */
    private String     funcName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_FATHER_ID
     *
     * @mbggenerated
     */
    private String     funcFatherId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_DESC
     *
     * @mbggenerated
     */
    private String     funcDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_REMARK
     *
     * @mbggenerated
     */
    private String     funcRemark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_DISABLE_TAG
     *
     * @mbggenerated
     */
    private String     funcDisableTag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_BY
     *
     * @mbggenerated
     */
    private String     funcCreateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_DATETIME
     *
     * @mbggenerated
     */
    private Date       funcCreateDatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_BY
     *
     * @mbggenerated
     */
    private String     funcUpdateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_DATETIME
     *
     * @mbggenerated
     */
    private Date       funcUpdateDatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_TAG
     *
     * @mbggenerated
     */
    private String     funcTag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_LEVEL
     *
     * @mbggenerated
     */
    private String     funcLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_URL
     *
     * @mbggenerated
     */
    private String     funcUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_ICON
     *
     * @mbggenerated
     */
    private String     funcIcon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_BTS_SYS_FUNCTION.FUNC_PRIORITY
     *
     * @mbggenerated
     */
    private BigDecimal funcPriority;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_ID
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_ID
     *
     * @mbggenerated
     */
    public String getFuncId() {
        return funcId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_ID
     *
     * @param funcId the value for TBL_BTS_SYS_FUNCTION.FUNC_ID
     *
     * @mbggenerated
     */
    public void setFuncId(String funcId) {
        this.funcId = funcId == null ? null : funcId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_NAME
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_NAME
     *
     * @mbggenerated
     */
    public String getFuncName() {
        return funcName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_NAME
     *
     * @param funcName the value for TBL_BTS_SYS_FUNCTION.FUNC_NAME
     *
     * @mbggenerated
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName == null ? null : funcName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_FATHER_ID
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_FATHER_ID
     *
     * @mbggenerated
     */
    public String getFuncFatherId() {
        return funcFatherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_FATHER_ID
     *
     * @param funcFatherId the value for TBL_BTS_SYS_FUNCTION.FUNC_FATHER_ID
     *
     * @mbggenerated
     */
    public void setFuncFatherId(String funcFatherId) {
        this.funcFatherId = funcFatherId == null ? null : funcFatherId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_DESC
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_DESC
     *
     * @mbggenerated
     */
    public String getFuncDesc() {
        return funcDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_DESC
     *
     * @param funcDesc the value for TBL_BTS_SYS_FUNCTION.FUNC_DESC
     *
     * @mbggenerated
     */
    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc == null ? null : funcDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_REMARK
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_REMARK
     *
     * @mbggenerated
     */
    public String getFuncRemark() {
        return funcRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_REMARK
     *
     * @param funcRemark the value for TBL_BTS_SYS_FUNCTION.FUNC_REMARK
     *
     * @mbggenerated
     */
    public void setFuncRemark(String funcRemark) {
        this.funcRemark = funcRemark == null ? null : funcRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_DISABLE_TAG
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_DISABLE_TAG
     *
     * @mbggenerated
     */
    public String getFuncDisableTag() {
        return funcDisableTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_DISABLE_TAG
     *
     * @param funcDisableTag the value for TBL_BTS_SYS_FUNCTION.FUNC_DISABLE_TAG
     *
     * @mbggenerated
     */
    public void setFuncDisableTag(String funcDisableTag) {
        this.funcDisableTag = funcDisableTag == null ? null : funcDisableTag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_BY
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_CREATE_BY
     *
     * @mbggenerated
     */
    public String getFuncCreateBy() {
        return funcCreateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_BY
     *
     * @param funcCreateBy the value for TBL_BTS_SYS_FUNCTION.FUNC_CREATE_BY
     *
     * @mbggenerated
     */
    public void setFuncCreateBy(String funcCreateBy) {
        this.funcCreateBy = funcCreateBy == null ? null : funcCreateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_DATETIME
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_CREATE_DATETIME
     *
     * @mbggenerated
     */
    public Date getFuncCreateDatetime() {
        return funcCreateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_CREATE_DATETIME
     *
     * @param funcCreateDatetime the value for TBL_BTS_SYS_FUNCTION.FUNC_CREATE_DATETIME
     *
     * @mbggenerated
     */
    public void setFuncCreateDatetime(Date funcCreateDatetime) {
        this.funcCreateDatetime = funcCreateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_BY
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_BY
     *
     * @mbggenerated
     */
    public String getFuncUpdateBy() {
        return funcUpdateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_BY
     *
     * @param funcUpdateBy the value for TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_BY
     *
     * @mbggenerated
     */
    public void setFuncUpdateBy(String funcUpdateBy) {
        this.funcUpdateBy = funcUpdateBy == null ? null : funcUpdateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_DATETIME
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_DATETIME
     *
     * @mbggenerated
     */
    public Date getFuncUpdateDatetime() {
        return funcUpdateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_DATETIME
     *
     * @param funcUpdateDatetime the value for TBL_BTS_SYS_FUNCTION.FUNC_UPDATE_DATETIME
     *
     * @mbggenerated
     */
    public void setFuncUpdateDatetime(Date funcUpdateDatetime) {
        this.funcUpdateDatetime = funcUpdateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_TAG
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_TAG
     *
     * @mbggenerated
     */
    public String getFuncTag() {
        return funcTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_TAG
     *
     * @param funcTag the value for TBL_BTS_SYS_FUNCTION.FUNC_TAG
     *
     * @mbggenerated
     */
    public void setFuncTag(String funcTag) {
        this.funcTag = funcTag == null ? null : funcTag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_LEVEL
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_LEVEL
     *
     * @mbggenerated
     */
    public String getFuncLevel() {
        return funcLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_LEVEL
     *
     * @param funcLevel the value for TBL_BTS_SYS_FUNCTION.FUNC_LEVEL
     *
     * @mbggenerated
     */
    public void setFuncLevel(String funcLevel) {
        this.funcLevel = funcLevel == null ? null : funcLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_URL
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_URL
     *
     * @mbggenerated
     */
    public String getFuncUrl() {
        return funcUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_URL
     *
     * @param funcUrl the value for TBL_BTS_SYS_FUNCTION.FUNC_URL
     *
     * @mbggenerated
     */
    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl == null ? null : funcUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_ICON
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_ICON
     *
     * @mbggenerated
     */
    public String getFuncIcon() {
        return funcIcon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_ICON
     *
     * @param funcIcon the value for TBL_BTS_SYS_FUNCTION.FUNC_ICON
     *
     * @mbggenerated
     */
    public void setFuncIcon(String funcIcon) {
        this.funcIcon = funcIcon == null ? null : funcIcon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_PRIORITY
     *
     * @return the value of TBL_BTS_SYS_FUNCTION.FUNC_PRIORITY
     *
     * @mbggenerated
     */
    public BigDecimal getFuncPriority() {
        return funcPriority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_BTS_SYS_FUNCTION.FUNC_PRIORITY
     *
     * @param funcPriority the value for TBL_BTS_SYS_FUNCTION.FUNC_PRIORITY
     *
     * @mbggenerated
     */
    public void setFuncPriority(BigDecimal funcPriority) {
        this.funcPriority = funcPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SYSFUNCVO that = (SYSFUNCVO) o;

        return funcId.equals(that.funcId);

    }

    @Override
    public int hashCode() {
        return funcId.hashCode();
    }
}