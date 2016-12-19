package com.cmpay.service.rule.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.Constants;
import com.cmpay.service.rule.bean.RuleResp;
import com.cmpay.service.rule.dao.CmpaySuppBankMapper;
import com.cmpay.service.rule.dao.CmpaySuppChannelMapper;
import com.cmpay.service.rule.model.CmpaySuppBank;
import com.cmpay.service.rule.model.CmpaySuppBankExample;
import com.cmpay.service.rule.model.CmpaySuppChannel;
import com.cmpay.service.rule.model.CmpaySuppChannelExample;
import com.cmpay.service.rule.service.RuleService;


/**
 * 路由商户配置相关实现类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月12日 下午1:35:59
 *
 */
@Service
public class RuleServiceImpl implements RuleService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpaySuppChannelMapper cmpaySuppChannelMapper;
    @Autowired
	CmpaySuppBankMapper cmpaySuppBankMapper;

	@Override
	public RuleResp queryIsSupByMer(String merchantId, String payCode,String bankCode) {
		logger.info("查询商户渠道是否合法，merchantId=[{}],payCode=[{}],bankCode=[{}]",merchantId,payCode,bankCode);
		RuleResp ruleResp=new RuleResp();
        try{
        	CmpaySuppChannelExample cmpayPayChannelExample=new CmpaySuppChannelExample();
        	cmpayPayChannelExample.createCriteria().andCodeEqualTo(payCode).andMerchNoEqualTo(merchantId);
        	List<CmpaySuppChannel> cmpayPayChannel=cmpaySuppChannelMapper.selectByExample(cmpayPayChannelExample);
        	if(null==cmpayPayChannel || cmpayPayChannel.size()==0){
        		logger.info("商户不支持所选的支付渠道");
        		ruleResp.setCode(Constants.TRADE_ERROR_8811_CODE);
            	ruleResp.setMsg(Constants.TRADE_ERROR_8811_MSG);
        	}else{
        		CmpaySuppChannel cpc=cmpayPayChannel.get(0);
                if(StringUtils.equals(Constants.ON, cpc.getOpenStatus())){
                	//根据渠道号和bankCode查询pcBankCode,后续放入redis中
                	CmpaySuppBankExample cmpaySuppBankExample=new CmpaySuppBankExample();
                	cmpaySuppBankExample.createCriteria().andPayChannelCodeEqualTo(payCode).andPayBankCodeEqualTo(bankCode);
                	List<CmpaySuppBank> list=cmpaySuppBankMapper.selectByExample(cmpaySuppBankExample);
                	if(null==list ||list.size()==0){
                		//支付渠道不支持该银行
                		ruleResp.setCode(Constants.TRADE_ERROR_8813_CODE);
                    	ruleResp.setMsg(Constants.TRADE_ERROR_8813_MSG);
                	}else{
                		CmpaySuppBank cmpaySuppBank=list.get(0);
                		ruleResp.setPcBankCode(cmpaySuppBank.getPcBankCode());
                		ruleResp.setPcBankName(cmpaySuppBank.getPcBankName());
                		ruleResp.setCode(Constants.SUCCESS_CODE);
                    	ruleResp.setMsg("商户支持该支付渠道");
                	}


                }else{
                	logger.info("该支付渠道已关闭！");
                	ruleResp.setCode(Constants.TRADE_ERROR_8812_CODE);
                	ruleResp.setMsg(Constants.TRADE_ERROR_8812_MSG);
                }
        	}

        }catch(Exception e){
        	logger.error("查询商户渠道出现异常！！！");
        	ruleResp.setCode(Constants.EXCEPTION_CODE);
        	ruleResp.setMsg(Constants.EXCEPTION_MSG);
        	e.printStackTrace();
        }
    	ruleResp.setPayWay(payCode);

		return ruleResp;
	}

	@Override
	public RuleResp payRule(String merchantId, String transAmt, String userId, String bankCode) {
		logger.info("自动路由支付渠道，merchantId=[{}],userId=[{}],transAmt=[{}],bankCode=[{}]",merchantId,userId,transAmt,bankCode);

		RuleResp ruleResp=new RuleResp();
        List<CmpaySuppChannel> avilist=new ArrayList<CmpaySuppChannel>();
        BigDecimal amount=new BigDecimal(transAmt);

		// 1.根据merchantId查询支付渠道列表
        CmpaySuppChannelExample cmpayPayChannelExample=new CmpaySuppChannelExample();
		cmpayPayChannelExample.createCriteria().andMerchNoEqualTo(merchantId).andOpenStatusEqualTo(Constants.ON);
		cmpayPayChannelExample.setOrderByClause("CREATE_TIME");
		List<CmpaySuppChannel> cpclist=cmpaySuppChannelMapper.selectByExample(cmpayPayChannelExample);

		if(cpclist==null || cpclist.size()==0){
			throw new TradeBizException(Constants.TRADE_ERROR_8815_CODE,Constants.TRADE_ERROR_8815_MSG);
		}

		avilist=cpclist;
		logger.info("该商户支持渠道数[{}]个，分别为【{}】",avilist.size(),avilist.toString());
       //2.判断是否有强制通道
         for(CmpaySuppChannel cmpayPayChannel:avilist){
            if(StringUtils.equals("1", cmpayPayChannel.getFlag())){
            	logger.info("商户【{}】强制指定走【{}】通道",merchantId,cmpayPayChannel.getCode());
            	avilist.clear();
            	avilist.add(cmpayPayChannel);
                break;
            }
         }

        //3.判断支持银行
        for(int i=0;i<avilist.size();i++){
        	CmpaySuppChannel cmpayPayChannel1=avilist.get(i);

    	   CmpaySuppBankExample cmpaySuppBankExample=new CmpaySuppBankExample();
       	   cmpaySuppBankExample.createCriteria().andPayChannelCodeEqualTo(cmpayPayChannel1.getCode()).andPayBankCodeEqualTo(bankCode).andStatusEqualTo(Constants.ON);
        	List<CmpaySuppBank> list=cmpaySuppBankMapper.selectByExample(cmpaySuppBankExample);
         	if(null==list ||list.size()==0){
       		  //支付渠道不支持该银行
         		logger.info("支付渠道【{}】不支持该银行【{}】",cmpayPayChannel1.getCode(),bankCode);
         		avilist.remove(i);
         		i--;
        	}else{
        		//4根据单笔限额过滤
        		CmpaySuppBank cmpaySuppBank=list.get(0);
                BigDecimal maxAmt=cmpaySuppBank.getSingleMaxAmount();
                BigDecimal minAmt=cmpaySuppBank.getSingleMinAmount();
                if(amount.compareTo(maxAmt)==1 || amount.compareTo(minAmt)==-1){
                	logger.info("支付渠道【{}】该银行【{}】最大限额为【{}】，最小限额为【{}】，交易金额为【{}】",cmpayPayChannel1.getCode(),bankCode,maxAmt,minAmt,amount);
                	avilist.remove(i);
             		i--;
                }

        	}
       }

        if(avilist.size()<1){
        	  logger.info("银行关闭或者订单金额超限");
        	  throw new TradeBizException(Constants.TRADE_ERROR_8816_CODE,Constants.TRADE_ERROR_8816_MSG);
        }


        //5.判断单日、单月限额，暂不实现，后续根据userId写入redis缓存中
        //6.判断单日、单月限制次数，暂不实现
        //7.计算支付渠道费率，低费率路由，暂不实现

        //8.判断优先级
        CmpaySuppChannel cmpaypc=null;
          if(avilist.size()>1){
        	  logger.info("可用支付渠道【{}】个，大于1个，根据优先级判断",avilist.size());
        	  int pri=-1;
              for(CmpaySuppChannel cmpayPayChannel2:avilist){

            	  String priority=cmpayPayChannel2.getPriority();
            	 int i=Integer.valueOf(priority);
                 if(i>pri){
                	 pri=i;
                	 cmpaypc= cmpayPayChannel2;
                 }

              }

          }

          if(avilist.size()==1){
        	  cmpaypc=avilist.get(0);
          }

          if(avilist.size()<1){
        	  logger.info("商户[{}]没有可用渠道完成该笔订单！",merchantId);
        	  throw new TradeBizException(Constants.TRADE_ERROR_8815_CODE,Constants.TRADE_ERROR_8815_MSG);
          }

          CmpaySuppBankExample cmpaySuppBankExample2=new CmpaySuppBankExample();
          cmpaySuppBankExample2.createCriteria().andPayChannelCodeEqualTo(cmpaypc.getCode()).andPayBankCodeEqualTo(bankCode).andStatusEqualTo(Constants.ON);
       	  List<CmpaySuppBank> csblist=cmpaySuppBankMapper.selectByExample(cmpaySuppBankExample2);

       	  ruleResp.setCode(Constants.SUCCESS_CODE);
       	  ruleResp.setMsg("ok");
          ruleResp.setPayWay(cmpaypc.getCode());
          ruleResp.setPcBankCode(csblist.get(0).getPcBankCode());
          ruleResp.setPcBankName(csblist.get(0).getPcBankName());

	    	return ruleResp;
	}

}
