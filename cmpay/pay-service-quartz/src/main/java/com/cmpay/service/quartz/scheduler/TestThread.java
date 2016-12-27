package com.cmpay.service.quartz.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月22日 下午1:33:39
 *
 */
public class TestThread implements Runnable {

	private int taskNum;
	public TestThread(int taskNum){
		this.taskNum=taskNum;
	}
	public TestThread(){
	}


//	@Override
//	public void run() {
//		for(int i=0;i<5;i++){
//			System.out.println(Thread.currentThread().getName()+"运行："+i);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}

	@Override
	public void run() {

			System.out.println(Thread.currentThread().getName()+",正在执行task："+taskNum);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
//		new Thread(new TestThread()).start();
//		new Thread(new TestThread()).start();


		//测试线程池
      //  ThreadPoolExecutor executor=new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

		  ExecutorService executor=Executors.newFixedThreadPool(10);
         for(int i=0;i<15;i++){
        	 TestThread testThread=new TestThread(i);
        	 executor.execute(testThread);
//        	 System.out.println("线程池中线程数目："+executor.getPoolSize()+",缓存队列中等待执行的任务数目："+executor.getQueue().size()+",已执行完的任务数目："+executor.getCompletedTaskCount());
//        	 System.out.println("线程池中线程数目："+executor.getPoolSize()+",缓存队列中等待执行的任务数目："+executor.getQueue().size()+",已执行完的任务数目："+executor.getCompletedTaskCount());

         }
         executor.shutdown();
	}





}
