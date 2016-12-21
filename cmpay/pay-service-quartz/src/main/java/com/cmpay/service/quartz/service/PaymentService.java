package com.cmpay.service.quartz.service;

import java.util.List;

import com.cmpay.service.quartz.model.CmapyCutOrder;

/**
 * 支付轮询任务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月19日 上午11:34:29
 *
 */
public interface PaymentService {

	/**
	 * 查询微信待处理中订单
	 */
	public void QueryWXOrder();

	/**
	 * 处理超时过期订单
	 */
	public void doExpireOrder();

    /**
     * 查询处理中的代扣订单
     * @return
     */
	public List<CmapyCutOrder> queryCutOrderList();

	public void doCutOrderTask(CmapyCutOrder cmapyCutOrder);

}
