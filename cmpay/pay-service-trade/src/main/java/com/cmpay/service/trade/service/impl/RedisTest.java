package com.cmpay.service.trade.service.impl;

import java.io.IOException;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年2月22日 上午11:10:01
 *
 */
public class RedisTest {

	public static void main(String[] args) throws IOException {
//		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-mybatis.xml","spring/spring-redis-cluster.xml"});
//	        context.start();
//	        RedisTemplate redisTemplate = (RedisTemplate)context.getBean("redisTemplate"); // 获取远程服务代理

//	        ValueOperations<String,Double> valueops= redisTemplate.opsForValue();
//            double i=valueops.increment(RedisConstants.CMPAY_DAYAMOUNT_+"gkk1",3.1);

//	        String i= redisTemplate.boundValueOps(RedisConstants.CMPAY_DAYAMOUNT_+"gkk1").get(0,-1);
//            System.out.println("i======="+i);

//	        System.out.println(new RedisTest().getIncrValue(redisTemplate, RedisConstants.CMPAY_DAYAMOUNT_+"gkk1"));


//add
//	        for(int i=0;i<5;i++){
//	        	redisTemplate.opsForValue().set("aaa_test_"+i, i);
//	        }

	        //批量删除
//	        Set<String> set=redisTemplate.keys("aaa_test_*");
//	        System.out.println("即将批量删除条数："+set.size());
//	        Iterator<String> it=set.iterator();
//	        while(it.hasNext()){
//	        	String keyStr=it.next();
//	        	System.out.println("keyStr="+keyStr);
//	        	redisTemplate.delete(keyStr);
//	        }

//	        System.in.read();


	System.out.println(false==false);

	}

	public long getIncrValue(final RedisTemplate redisTemplate,final String key) {

	    return (long) redisTemplate.execute(new RedisCallback<Long>() {
	        @Override
	        public Long doInRedis(RedisConnection connection) throws DataAccessException {
	            RedisSerializer<String> serializer=redisTemplate.getStringSerializer();
	            byte[] rowkey=serializer.serialize(key);
	            byte[] rowval=connection.get(rowkey);
	            try {
	                String val=serializer.deserialize(rowval);
	                return Long.parseLong(val);
	            } catch (Exception e) {
	                return 0L;
	            }
	        }
	    });
	}

}
