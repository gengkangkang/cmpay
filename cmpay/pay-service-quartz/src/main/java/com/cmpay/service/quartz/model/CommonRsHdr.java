package com.cmpay.service.quartz.model;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午4:07:35
 *
 */
public class CommonRsHdr {

	    private String StatusCode;
	    private String ServerStatusCode;
	    private String SPRsUID;
	    private String RqUID;

		public String getStatusCode() {
			return StatusCode;
		}
		public void setStatusCode(String statusCode) {
			StatusCode = statusCode;
		}
		public String getServerStatusCode() {
			return ServerStatusCode;
		}
		public void setServerStatusCode(String serverStatusCode) {
			ServerStatusCode = serverStatusCode;
		}
		public String getSPRsUID() {
			return SPRsUID;
		}
		public void setSPRsUID(String sPRsUID) {
			SPRsUID = sPRsUID;
		}
		@Override
		public String toString() {
			return "CommonRsHdr [StatusCode=" + StatusCode + ", ServerStatusCode=" + ServerStatusCode + ", SPRsUID="
					+ SPRsUID + ", RqUID=" + RqUID + "]";
		}
		public String getRqUID() {
			return RqUID;
		}
		public void setRqUID(String rqUID) {
			RqUID = rqUID;
		}
}
