//package com.cmpay.service.trade.service.impl;
//
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.cmpay.service.trade.conf.TestDisconf;
//
///**
// * @author gengkangkang
// * @E-mail gengkangkang@cm-inv.com
// *
// * 2017年2月14日 下午5:02:56
// *
// */
//@Service
//public class TestDisconfService implements InitializingBean, DisposableBean {
//
//	@Autowired
//	private TestDisconf testDisconf;
//
//	@Override
//	public void destroy() throws Exception {
//		System.out.println("調用destroy方法======================");
//
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		System.out.println("調用afterPropertiesSet方法============");
//
//		System.out.println("host======================"+testDisconf.getHost());
//		System.out.println("prot======================"+testDisconf.getPort());
//
//	}
//
//	public void testPro(){
//		System.out.println("host======================"+testDisconf.getHost());
//		System.out.println("prot======================"+testDisconf.getPort());
//
//	}
//
//}
