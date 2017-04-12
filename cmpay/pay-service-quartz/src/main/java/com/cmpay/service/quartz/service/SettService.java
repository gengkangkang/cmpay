package com.cmpay.service.quartz.service;

import java.util.List;

import com.cmpay.service.quartz.model.CmapySettTotal;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月10日 下午5:40:38
 *
 */
public interface SettService {

	public void doDaliyMerSettDetail();
	public void doDaliyMerSettTotal();
	public void doMerSett(CmapySettTotal cmapySettTotal);
	public List<CmapySettTotal> queryMerSett();


}
