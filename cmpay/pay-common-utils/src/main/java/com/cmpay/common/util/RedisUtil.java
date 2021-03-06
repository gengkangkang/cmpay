package com.cmpay.common.util;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月6日 下午4:21:30
 *
 * Redis工具类
 *
 */
@Service
public class RedisUtil {

	private Logger logger=Logger.getLogger(RedisUtil.class);

	@Autowired
	private RedisTemplate redisTemplate;

	 /**
	  * 通过key获取值
	  * @param key
	  * @return
	  */
	   public Object get(String key){
	        Object value = null;
	        try {
	        	 ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
	             value = valueops.get(key);
	        } catch (Exception e) {
	        	logger.error("从redis获取值异常");
	        	e.printStackTrace();
	        }
	        return value;
	    }

	   public boolean set(String key,Object value){
	        boolean flag = false;
	        try {
	        	 ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
	        	 valueops.set(key, value);
	        	 flag=true;
	        } catch (Exception e) {
	        	logger.error("redis设置值异常");
	        	e.printStackTrace();
	        }
	        return flag;
	    }


	   public boolean del(String key){
	        boolean flag = false;
	        try {
	        	redisTemplate.delete(key);
	        	 flag=true;
	        } catch (Exception e) {
	        	logger.error("从redis删除值异常");
	        	e.printStackTrace();
	        }
	        return flag;
	    }

	   public boolean add(String key,double value){
	        boolean flag = false;
	        try {
	        	ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
	        	valueops.increment(key, value);
	        	 flag=true;
	        } catch (Exception e) {
	        	logger.error("从redis删除值异常");
	        	e.printStackTrace();
	        }
	        return flag;
	    }

	   public Object getIncrValue(String key){
	        Object value = null;
	        try {
	             value = redisTemplate.boundValueOps(key).get(0, -1);
	        } catch (Exception e) {
	        	logger.error("从redis获取值异常");
	        	e.printStackTrace();
	        }
	        return value;
	    }

	   public boolean delByPreStr(String key){
	        boolean flag = false;
	        try {
	        	 Set<String> set=redisTemplate.keys(key+"*");
	 	        logger.info("即将批量删除条数为："+set.size());
	 	        Iterator<String> it=set.iterator();
	 	        while(it.hasNext()){
	 	        	String keyStr=it.next();
	 	        	logger.info("删除keyStr="+keyStr);
	 	        	redisTemplate.delete(keyStr);
	 	        }
	        	 flag=true;
	        } catch (Exception e) {
	        	logger.error("从redis批量删除值异常");
	        	e.printStackTrace();
	        }
	        return flag;
	    }

	public static void main(String[] args) throws Exception {
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
	        context.start();

	        RedisTemplate redisTemplate = (RedisTemplate)context.getBean("redisTemplate"); // 获取远程服务代理
//	        ValueOperations<String, String> valueops = redisTemplate.opsForValue();
//            System.out.println("向redis写入值");
//	        valueops.set("gkk", "gengkangkang");
//            System.out.println("从redis取值");
//            String name=valueops.get("gkk");
//            System.out.println("name===="+name);


//	        ValueOperations<String, List<String>> valueops = redisTemplate.opsForValue();
//	        List<String> ipList=new ArrayList<String>();
//	        ipList.add("10.17.5.65");
//	        ipList.add("10.17.5.66");
//	        ipList.add("10.17.5.67");
//
//            System.out.println("向redis写入值");
//	        valueops.set("ipList", ipList);
//            System.out.println("从redis取值");
//            List<String> ips=valueops.get("ipList");
//            System.out.println("ips===="+ips);
//            for(String ip:ips){
//            	System.out.println(ip);
//            }

//	        Thread.sleep(10);
//	        RedisUtil ru=new RedisUtil();
//	        System.out.println("写入redis");
//	        ru.set("test","哈哈");
//	        System.out.println("根据key获取值");
//	        String str=(String) ru.get("test");
//	        System.out.println("str==="+str);
//	        System.out.println("根据key删除");
//	        ru.del("test");
//	        System.out.println("删除后str=="+ru.get("test"));



	        //测试加法
            ValueOperations<String,Integer> valueops= redisTemplate.opsForValue();
            valueops.increment(RedisConstants.CMPAY_DAYAMOUNT_+"gkk", 2);

            Integer i=valueops.get(RedisConstants.CMPAY_DAYAMOUNT_+"gkk");
            System.out.println("i======="+i);



	}

}
