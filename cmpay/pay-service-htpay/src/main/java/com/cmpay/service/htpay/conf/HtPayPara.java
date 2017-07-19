package com.cmpay.service.htpay.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
/**
 * 航天支付配置类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年6月29日 下午5:47:33
 *
 */
@Service
@DisconfFile(filename = "htpay.properties")
@DisconfUpdateService(classes = { HtPayPara.class })
public class HtPayPara implements IDisconfUpdate {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String htpay_sinCutReqUrl;
	private String htpay_sinCutQueryReqUrl;
	private String htpay_sinCutVersion;
	private String htpay_encoding;
	private String htpay_signType;



	@Override
	public void reload() throws Exception {
		logger.info("航天支付配置文件htpay.properties更新了！！！！！！");
	}

	@DisconfFileItem(name = "htpay_sinCutReqUrl", associateField = "htpay_sinCutReqUrl")
	public String getHtpay_sinCutReqUrl() {
		return htpay_sinCutReqUrl;
	}

	public void setHtpay_sinCutReqUrl(String htpay_sinCutReqUrl) {
		this.htpay_sinCutReqUrl = htpay_sinCutReqUrl;
	}

	@DisconfFileItem(name = "htpay_sinCutQueryReqUrl", associateField = "htpay_sinCutQueryReqUrl")
	public String getHtpay_sinCutQueryReqUrl() {
		return htpay_sinCutQueryReqUrl;
	}

	public void setHtpay_sinCutQueryReqUrl(String htpay_sinCutQueryReqUrl) {
		this.htpay_sinCutQueryReqUrl = htpay_sinCutQueryReqUrl;
	}

	@DisconfFileItem(name = "htpay_sinCutVersion", associateField = "htpay_sinCutVersion")
	public String getHtpay_sinCutVersion() {
		return htpay_sinCutVersion;
	}

	public void setHtpay_sinCutVersion(String htpay_sinCutVersion) {
		this.htpay_sinCutVersion = htpay_sinCutVersion;
	}

	@DisconfFileItem(name = "htpay_encoding", associateField = "htpay_encoding")
	public String getHtpay_encoding() {
		return htpay_encoding;
	}

	public void setHtpay_encoding(String htpay_encoding) {
		this.htpay_encoding = htpay_encoding;
	}


	@DisconfFileItem(name = "htpay_signType", associateField = "htpay_signType")
	public String getHtpay_signType() {
		return htpay_signType;
	}
	public void setHtpay_signType(String htpay_signType) {
		this.htpay_signType = htpay_signType;
	}

}
