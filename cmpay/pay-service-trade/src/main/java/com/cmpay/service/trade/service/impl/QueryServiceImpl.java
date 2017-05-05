package com.cmpay.service.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.Constants;
import com.cmpay.common.util.RedisConstants;
import com.cmpay.common.util.RedisUtil;
import com.cmpay.facade.entity.QueryLimitAmtRq;
import com.cmpay.facade.entity.QueryLimitAmtRs;
import com.cmpay.facade.trade.QueryService;
import com.cmpay.service.rule.dao.CmpaySuppChannelMapper;
import com.cmpay.service.rule.model.CmpaySuppChannel;
import com.cmpay.service.rule.model.CmpaySuppChannelExample;
import com.cmpay.service.trade.dao.CmpayCardBinMapper;
import com.cmpay.service.trade.dao.CmpayPayChannelMapper;
import com.cmpay.service.trade.dao.CmpaySuppBankMapMapper;
import com.cmpay.service.trade.model.CmpayCardBin;
import com.cmpay.service.trade.model.CmpayCardBinKey;
import com.cmpay.service.trade.model.CmpaySuppBankMap;
import com.cmpay.service.trade.model.CmpaySuppBankMapExample;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年5月2日 下午2:07:46
 *
 */
@Service
public class QueryServiceImpl implements QueryService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpayPayChannelMapper cmpayPayChannelMapper;
	@Autowired
	CmpaySuppBankMapMapper cmpaySuppBankMapMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	CmpayCardBinMapper cmpayCardBinMapper;
	@Autowired
	CmpaySuppChannelMapper cmpaySuppChannelMapper;


	@Override
	public QueryLimitAmtRs queryLimtAmt(QueryLimitAmtRq queryLimitAmtRq) {
		logger.info("查询限额功能请求参数：【{}】",queryLimitAmtRq.toString());
		QueryLimitAmtRs queryLimitAmtRs=new QueryLimitAmtRs();
		Map<String,String> data=null;
		try{
		String bankCode=queryLimitAmtRq.getBankCode();
		String merId=queryLimitAmtRq.getMerId();
		String inchannel=queryLimitAmtRq.getInchannel();
		String cardNo=queryLimitAmtRq.getCardNo();
		if(StringUtils.isBlank(merId)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(cardNo)){
			 logger.info("缺少必要参数");
        	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,Constants.PARA_ERROR_8905_MSG);
		}

        //如果bankCode不为空，则取bankCode,否则根据卡bin查找
		if(StringUtils.isBlank(bankCode)){
	        if(!CmpayUtils.isCardNo(cardNo)){
	            logger.info("银行卡号非法");
	        	 throw new TradeBizException(Constants.TRADE_ERROR_8825_CODE,Constants.TRADE_ERROR_8825_MSG);
	        }

	        for(int i=4;i<=10;i++){
	      	   String cardBin=cardNo.substring(0,i);
	      	   if(i==4 && !"9558".equals(cardBin))
	      		   continue;

	      	     data=(Map<String,String>) redisUtil.get(RedisConstants.CMPAY_CARDBIN_+cardBin+"_"+cardNo.length());
	             if(data!=null){
	            	 logger.info("卡信息【{}】",data.toString());
	            	 bankCode=data.get(RedisConstants.bankCode);
	             }
	         }
	        //如果缓存未命中，查数据库
	        CmpayCardBinKey cmpayCardBinKey=null;
	        if(data==null){
	          logger.info("缓存中没查到卡信息，去数据库中查询");
	          for(int i=4;i<=10;i++){
	         	   String cardBin=cardNo.substring(0,i);
	         	   if(i==4 && !"9558".equals(cardBin))
	         		   continue;

	         	     cmpayCardBinKey=new CmpayCardBinKey();
	         	     cmpayCardBinKey.setCardBin(cardBin);
	         	     cmpayCardBinKey.setCardLength(new BigDecimal(cardNo.length()));
	         	     CmpayCardBin cmpayCardBin= cmpayCardBinMapper.selectByPrimaryKey(cmpayCardBinKey);
	                if(cmpayCardBin!=null){
	               	 logger.info("从数据库load卡信息【{}】",cmpayCardBin.toString());
	               	bankCode=cmpayCardBin.getCmpayCode();

	               	 try{
	                    	redisUtil.set(RedisConstants.CMPAY_CARDBIN_+cmpayCardBin.getCardBin()+"_"+cmpayCardBin.getCardLength(), data);
	               	 }catch(Exception e){
	               		 logger.error("将卡信息加载到redis异常！！！！",e);
	               	 }
	                }
	            }

	        }

		}
		//根据商户号查询所支持的支付渠道，根据算法筛选出单日限额最大的渠道（后续考虑把限额信息放入缓存）

	        CmpaySuppChannelExample cmpayPayChannelExample=new CmpaySuppChannelExample();
			cmpayPayChannelExample.createCriteria().andMerchNoEqualTo(merId).andOpenStatusEqualTo(Constants.ON);
			cmpayPayChannelExample.setOrderByClause("CREATE_TIME");
			List<CmpaySuppChannel> cpclist=cmpaySuppChannelMapper.selectByExample(cmpayPayChannelExample);

			if(cpclist==null || cpclist.size()==0){
				throw new TradeBizException(Constants.TRADE_ERROR_8815_CODE,Constants.TRADE_ERROR_8815_MSG);
			}

         //判断支持银行，取日限额最大的渠道
			for(int i=0;i<cpclist.size();i++){
			  	CmpaySuppChannel cmpayPayChannel=cpclist.get(i);

		    	   CmpaySuppBankMapExample cmpaySuppBankExample=new CmpaySuppBankMapExample();
		       	   cmpaySuppBankExample.createCriteria().andPayChannelCodeEqualTo(cmpayPayChannel.getCode()).andPayBankCodeEqualTo(bankCode).andStatusEqualTo(Constants.ON);
		        	List<CmpaySuppBankMap> list=cmpaySuppBankMapMapper.selectByExample(cmpaySuppBankExample);
		         	if(null==list ||list.size()==0){
		       		  //支付渠道不支持该银行
		         		logger.info("支付渠道【{}】不支持该银行【{}】",cmpayPayChannel.getCode(),bankCode);
		         		cpclist.remove(i);
		         		i--;
		        	}

			}

		       if(cpclist.size()<1){
		        	  logger.info("银行关闭");
		        	  throw new TradeBizException(Constants.TRADE_ERROR_8816_CODE,Constants.TRADE_ERROR_8816_MSG);
		        }
     //next


		        CmpaySuppChannel cmpaypc=null;
                BigDecimal remAmt=BigDecimal.ZERO;

		          if(cpclist.size()>0){
		        	  logger.info("可用支付渠道【{}】个，大于1个，根据当日可用余额判断",cpclist.size());

						for(int i=0;i<cpclist.size();i++){
						  	CmpaySuppChannel cmpayPayChannel=cpclist.get(i);

					    	   CmpaySuppBankMapExample cmpaySuppBankExample=new CmpaySuppBankMapExample();
					       	   cmpaySuppBankExample.createCriteria().andPayChannelCodeEqualTo(cmpayPayChannel.getCode()).andPayBankCodeEqualTo(bankCode).andStatusEqualTo(Constants.ON);
					        	List<CmpaySuppBankMap> list=cmpaySuppBankMapMapper.selectByExample(cmpaySuppBankExample);
					        	CmpaySuppBankMap cmpaySuppBankMap=list.get(0);
					        	BigDecimal dayAmt=cmpaySuppBankMap.getDayMaxAmount();
						          //获取该卡号当日可用余额
							    String useAmount="0";
						        try{
						        	useAmount=(String) redisUtil.getIncrValue(RedisConstants.CMPAY_DAYAMOUNT_+cardNo+cmpayPayChannel.getCode());
						        }catch(Exception e){
						        	logger.info("获取用户单日消费金额失败！！！",e);
						        }
						        if(StringUtils.isBlank(useAmount)){
						        	useAmount="0";
						        }
						        BigDecimal ramt=dayAmt.subtract(new BigDecimal(useAmount));
					        	if(ramt.compareTo(remAmt)==1){
					        		remAmt=ramt;
                                   cmpaypc=cmpayPayChannel;
					        	}


						}

		          }



	        CmpaySuppBankMapExample cmpaySuppBankExample1=new CmpaySuppBankMapExample();
	        cmpaySuppBankExample1.createCriteria().andPayChannelCodeEqualTo(cmpaypc.getCode()).andPayBankCodeEqualTo(bankCode).andStatusEqualTo(Constants.ON);
	        	List<CmpaySuppBankMap> list1=cmpaySuppBankMapMapper.selectByExample(cmpaySuppBankExample1);

	        	CmpaySuppBankMap csbm=list1.get(0);

	        queryLimitAmtRs.setRespCode(Constants.SUCCESS_CODE);
	        queryLimitAmtRs.setRespMsg(Constants.SUCCESS_OK);
	        queryLimitAmtRs.setCardNo(cardNo);
	        queryLimitAmtRs.setBankCode(bankCode);
	        queryLimitAmtRs.setPayCode(cmpaypc.getCode());
	        queryLimitAmtRs.setSingleAmt(csbm.getSingleMaxAmount().toString());
	        queryLimitAmtRs.setDayAmt(csbm.getDayMaxAmount().toString());
	        queryLimitAmtRs.setMonthAmt(csbm.getDayMaxAmount().toString());
	        queryLimitAmtRs.setAvailableAmt(remAmt.toString());

		}catch(TradeBizException tbe){
			logger.info("业务异常code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
			queryLimitAmtRs.setRespCode(tbe.getCode());
			queryLimitAmtRs.setRespMsg(tbe.getMsg());
		}catch(Exception e){
			logger.error("查询限额信息异常！",e);
			queryLimitAmtRs.setRespCode(Constants.TRADE_ERROR_C9999_CODE);
			queryLimitAmtRs.setRespMsg(Constants.TRADE_ERROR_C9999_MSG);
		}

		return queryLimitAmtRs;
	}

}
