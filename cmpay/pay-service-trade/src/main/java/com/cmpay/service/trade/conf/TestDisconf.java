package com.cmpay.service.trade.conf;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年2月14日 下午4:46:18
 *
 */
@Service
@DisconfFile(filename = "testDisconf.properties")
@DisconfUpdateService(classes = {TestDisconf.class})
public class TestDisconf implements IDisconfUpdate {
	private String host;
	private String port;

	@DisconfFileItem(name = "test.host", associateField = "host")
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

    @DisconfFileItem(name = "test.port", associateField = "port")
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	@Override
	public void reload() throws Exception {
		System.out.println("TestDisconf更新了！！！！！！");
		System.out.println("host============"+host);
		System.out.println("prot============"+port);

	}

}
