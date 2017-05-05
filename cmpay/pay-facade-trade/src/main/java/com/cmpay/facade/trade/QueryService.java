package com.cmpay.facade.trade;

import com.cmpay.facade.entity.QueryLimitAmtRq;
import com.cmpay.facade.entity.QueryLimitAmtRs;

/**
 *  查询服务
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年5月2日 下午1:54:11
 *
 */
public interface QueryService {

	public QueryLimitAmtRs queryLimtAmt(QueryLimitAmtRq queryLimitAmtRq);

}
