package com.cmpay.service.trade.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpay.common.exception.BizException;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月10日 下午2:10:26
 *
 * 交易模块异常类
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
		logger.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}

}
