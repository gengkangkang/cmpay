package com.cmpay.service.quartz.util;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月10日 下午1:26:30
 *
 */
public class Test {
	private  Logger logger=LoggerFactory.getLogger(this.getClass());

	public void hello(){
		try{
		Map map=null;
		 String msg=(String) map.get("msg");
		 System.out.println(msg);
		}catch(Exception e){
			logger.error("出错了：[{}]","信息", e);
		}
	}

	public void test() throws Exception{
		 throw new Exception("线程中抛出异常3");
	}

	public static void main(String[] args) throws Exception {
//		new Test().hello();

		ExecutorService es = Executors.newFixedThreadPool(1);//线程数默认20
		for( int i=0;i<5;i++){
            System.out.println("[i=]"+i);

			es.execute(new Runnable(){
				@Override
				public void run() {
					try {
                         System.out.println("哈哈");
//                         throw new Exception("线程中抛出异常");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} );
		}
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);

	}

}
