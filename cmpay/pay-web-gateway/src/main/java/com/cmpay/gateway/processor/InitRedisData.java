package com.cmpay.gateway.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpay.common.util.RedisConstants;
import com.cmpay.common.util.RedisUtil;
import com.cmpay.gateway.dao.CmpayCardBinMapper;
import com.cmpay.gateway.dao.CmpayIpBindingMapper;
import com.cmpay.gateway.dao.MerConfigMapper;
import com.cmpay.gateway.model.CmpayCardBin;
import com.cmpay.gateway.model.CmpayIpBinding;
import com.cmpay.gateway.model.MerConfig;
import com.cmpay.gateway.model.MerConfigExample;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月22日 下午3:17:25
 *
 */
public class InitRedisData {

	private Logger logger=Logger.getLogger(this.getClass());

    @Autowired
    private CmpayIpBindingMapper cmpayIpBindingMapper;
    @Autowired
    private CmpayCardBinMapper cmpayCardBinMapper;
    @Autowired
    private MerConfigMapper merConfigMapper;
    @Autowired
    private RedisUtil redisUtil;

	 @PostConstruct
     public void initData(){
        logger.info("===================初始化绑定IP数据 start======================");
        try{
        List<CmpayIpBinding> ipList=cmpayIpBindingMapper.getIpList();
         System.out.println("--------------------:"+ipList.size());
         boolean flag = redisUtil.set(RedisConstants.CMPAY_IP_BINDING_LIST, ipList);
         logger.info("初始化iplist状态："+flag);
        }catch(Exception e){
        	logger.error("初始化iplist异常！！！！！！");
        	e.printStackTrace();
        }
//         System.out.println("======测试取数据=====");
//         List<CmpayIpBinding> res=(List<CmpayIpBinding>) redisUtil.get(RedisConstants.CMPAY_IP_BINDING_LIST);
//          System.out.println("list=="+res.toString());
//          for(CmpayIpBinding cip:res){
//        	  System.out.println(cip.getIp());
//        	  System.out.println(cip.getCreatetime());
//
//          }
//          System.out.println("测试删除数据=========");
//          boolean delflag=redisUtil.del(RedisConstants.CMPAY_IP_BINDING_LIST);
//          System.out.println("删除状态："+delflag);
        logger.info("===================初始化绑定IP数据 end======================");
        MerConfigExample merConfigExample=new MerConfigExample();
        List<MerConfig> mcList=merConfigMapper.selectByExample(merConfigExample);
        for(MerConfig merConfig:mcList){
        	redisUtil.set(RedisConstants.CMPAY_MD5KEY_+merConfig.getMerchantid(), merConfig.getPartnerkey());
        }

        logger.info("===================初始化MD5数据 start======================");
        try{

        }catch(Exception e){
        	logger.error("初始化MD5数据异常！！！！");
        	e.printStackTrace();
        }

        logger.info("===================初始化MD5数据 END======================");




        logger.info("===================初始化卡bin数据 start======================");
            try{

            	List<CmpayCardBin> cardBinList=cmpayCardBinMapper.getCardBinList();
            	Map<String,String> map=null;
                for(CmpayCardBin cmpayCardBin:cardBinList){
                	map=new HashMap<String,String>();
                	map.put(RedisConstants.bankCode, cmpayCardBin.getCmpayCode());
                	map.put(RedisConstants.bankName, cmpayCardBin.getCmpayName());
                	map.put(RedisConstants.cardType, cmpayCardBin.getCardType());
                	redisUtil.set(RedisConstants.CMPAY_CARDBIN_+cmpayCardBin.getCardBin()+"_"+cmpayCardBin.getCardLength(), map);
                }

            }catch(Exception e){
            	logger.error("初始化卡bin数据异常！！！！");
            	e.printStackTrace();
            }
        logger.info("===================初始化卡bin数据 end======================");



     }

}
