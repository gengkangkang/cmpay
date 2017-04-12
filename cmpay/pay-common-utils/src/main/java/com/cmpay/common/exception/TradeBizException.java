package com.cmpay.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月6日 下午5:19:20
 *
 */
public class TradeBizException extends BizException {

	private static final long serialVersionUID = 1L;

	private Logger logger=LoggerFactory.getLogger(getClass());


	public TradeBizException() {
	}

	public TradeBizException(String code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public TradeBizException(String code, String msg) {
		super(code, msg);
	}

	public TradeBizException print() {
		logger.info("==>TradeBizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}

}
