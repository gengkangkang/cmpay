package com.cmpay.service.quartz.model;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午4:05:06
 *
 */
public class CommonRqHdr {

    private String RqUID;
    private String SPName;
    private String NumTranCode;
    private String ClearDate;
    private String TranDate;
    private String TranTime;
    private String ChannelId;
    private String Version;

	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		RqUID = rqUID;
	}
	public String getSPName() {
		return SPName;
	}
	public void setSPName(String sPName) {
		SPName = sPName;
	}
	public String getNumTranCode() {
		return NumTranCode;
	}
	public void setNumTranCode(String numTranCode) {
		NumTranCode = numTranCode;
	}
	public String getClearDate() {
		return ClearDate;
	}
	public void setClearDate(String clearDate) {
		ClearDate = clearDate;
	}
	public String getTranDate() {
		return TranDate;
	}
	public void setTranDate(String tranDate) {
		TranDate = tranDate;
	}
	public String getTranTime() {
		return TranTime;
	}
	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}
	public String getChannelId() {
		return ChannelId;
	}
	public void setChannelId(String channelId) {
		ChannelId = channelId;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}

}
