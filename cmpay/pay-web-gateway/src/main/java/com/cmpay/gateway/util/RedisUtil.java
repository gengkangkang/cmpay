package com.cmpay.gateway.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.cmpay.common.util.RedisConstants;
import com.cmpay.gateway.model.CmpayCardBin;

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
	        	 ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
	        	 flag=true;
	        } catch (Exception e) {
	        	logger.error("从redis获取值异常");
	        	e.printStackTrace();
	        }
	        return flag;
	    }


	public static void main(String[] args) throws Exception {
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf/spring.xml"});
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


	        //测试卡bin
           String cardno="6228482978638035076";
           System.out.println("cardno="+cardno);
           for(int i=4;i<=10;i++){
        	   String cardBin=cardno.substring(0,i);
        	   if(i==4 && !"9558".equals(cardBin))
        		   continue;

        	   ValueOperations<String, CmpayCardBin> valueops = redisTemplate.opsForValue();
        	   CmpayCardBin c=valueops.get(RedisConstants.CMPAY_CARDBIN_+cardBin+"_"+cardno.length());
               if(c!=null){
            	   System.out.println("查出来了，"+c.toString());
            	   return;
               }
           }
                 System.out.println("没查到");

	}

//    public UpCardbin getCardBinByCardNO(String cardNo){
//    	logger.info("根据cardno={}查询卡bin信息",cardNo);
//        for (int i = 4; i <= 10; i++) {
//            String cardBin = cardNo.substring(0, i);
//            if (i == 4 && !"9558".equals(cardBin))
//               continue;
//
//        	List<UpCardbin> binList = new JPAQuery(em).from(qUpCardbin).where(
//        			qUpCardbin.cardBin.eq(cardBin)
//    				).list(qUpCardbin);
//
//        	if(binList.size()>0){
//        		return binList.get(0);
//        	}
//
//        }
//
//        logger.error("卡BIN查询数据库匹配失败，未找到{}卡号对应的卡BIN信息", cardNo);
//        return null;
//
//    }

}
