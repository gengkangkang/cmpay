package com.cmpay.service.chinapay.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

/**
 * 银联代付配置文件
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 *         2017年3月22日 上午10:11:54
 *
 */
@Service
@DisconfFile(filename = "chinapaysinpay.properties")
@DisconfUpdateService(classes = { ChinaPaySinPay.class })
public class ChinaPaySinPay implements IDisconfUpdate {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String pay_sfjSinPayReqUrl;
	private String pay_sfjSinPayQueryReqUrl;
	private String pay_sfjSinPayVersion;
	private String pay_merPrK;
	private String pay_pgPubk;
	private String pay_sfjEncoding;
	private String pay_sfjGateId;
	private String pay_pubId;

	@Override
	public void reload() throws Exception {
		logger.info("银联代付文件chinapaysinpay.properties更新了！！！！！！");

	}

	@DisconfFileItem(name = "pay_sfjSinPayReqUrl", associateField = "pay_sfjSinPayReqUrl")
	public String getPay_sfjSinPayReqUrl() {
		return pay_sfjSinPayReqUrl;
	}

	public void setPay_sfjSinPayReqUrl(String pay_sfjSinPayReqUrl) {
		this.pay_sfjSinPayReqUrl = pay_sfjSinPayReqUrl;
	}

	@DisconfFileItem(name = "pay_sfjSinPayQueryReqUrl", associateField = "pay_sfjSinPayQueryReqUrl")
	public String getPay_sfjSinPayQueryReqUrl() {
		return pay_sfjSinPayQueryReqUrl;
	}

	public void setPay_sfjSinPayQueryReqUrl(String pay_sfjSinPayQueryReqUrl) {
		this.pay_sfjSinPayQueryReqUrl = pay_sfjSinPayQueryReqUrl;
	}

	@DisconfFileItem(name = "pay_sfjSinPayVersion", associateField = "pay_sfjSinPayVersion")
	public String getPay_sfjSinPayVersion() {
		return pay_sfjSinPayVersion;
	}

	public void setPay_sfjSinPayVersion(String pay_sfjSinPayVersion) {
		this.pay_sfjSinPayVersion = pay_sfjSinPayVersion;
	}

	@DisconfFileItem(name = "pay_merPrK", associateField = "pay_merPrK")
	public String getPay_merPrK() {
		return pay_merPrK;
	}

	public void setPay_merPrK(String pay_merPrK) {
		this.pay_merPrK = pay_merPrK;
	}

	@DisconfFileItem(name = "pay_pgPubk", associateField = "pay_pgPubk")
	public String getPay_pgPubk() {
		return pay_pgPubk;
	}

	public void setPay_pgPubk(String pay_pgPubk) {
		this.pay_pgPubk = pay_pgPubk;
	}
	@DisconfFileItem(name = "pay_sfjEncoding", associateField = "pay_sfjEncoding")
	public String getPay_sfjEncoding() {
		return pay_sfjEncoding;
	}

	public void setPay_sfjEncoding(String pay_sfjEncoding) {
		this.pay_sfjEncoding = pay_sfjEncoding;
	}
	@DisconfFileItem(name = "pay_sfjGateId", associateField = "pay_sfjGateId")
	public String getPay_sfjGateId() {
		return pay_sfjGateId;
	}

	public void setPay_sfjGateId(String pay_sfjGateId) {
		this.pay_sfjGateId = pay_sfjGateId;
	}
	@DisconfFileItem(name = "pay_pubId", associateField = "pay_pubId")
	public String getPay_pubId() {
		return pay_pubId;
	}
	public void setPay_pubId(String pay_pubId) {
		this.pay_pubId = pay_pubId;
	}

}
