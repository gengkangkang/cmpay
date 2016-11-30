package com.cmpay.service.trade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.InChannelEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.common.util.Constants;
import com.cmpay.facade.trade.UpayService;
import com.cmpay.service.bank.service.PayAuthService;
import com.cmpay.service.trade.dao.CmpayPayChannelMapper;
import com.cmpay.service.trade.dao.CmpaySuppBankMapMapper;
import com.cmpay.service.trade.model.CmpayPayChannel;
import com.cmpay.service.trade.model.CmpayPayChannelExample;
import com.cmpay.service.trade.model.CmpaySuppBankMap;
import com.cmpay.service.trade.model.CmpaySuppBankMapExample;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月29日 上午10:34:13
 *
 */
@Service
public class UpayServiceImpl implements UpayService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpayPayChannelMapper cmpayPayChannelMapper;
	@Autowired
	CmpaySuppBankMapMapper cmpaySuppBankMapMapper;
	@Autowired
	PayAuthService payAuthService;


	@Override
	public Map<String, String> payAuth(String merchantId, String userId, String inchannel, String authChannel,
			String cardNo, String cardType, String idNo, String idType, String name, String bankMobile, String bankCode,
			String terminalType) {
		//1.校验参数
        String code=Constants.FAILED_CODE;
        String msg=null;
        Map<String,String> result=new HashMap<String,String>();
        if(StringUtils.isBlank(merchantId)||StringUtils.isBlank(userId)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(authChannel)
        		||StringUtils.isBlank(cardNo)||StringUtils.isBlank(cardType)||StringUtils.isBlank(idNo)||StringUtils.isBlank(idType)
        		||StringUtils.isBlank(name)||StringUtils.isBlank(bankMobile)||StringUtils.isBlank(bankCode)){
        	logger.info("请检查请求参数！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_MSG);
        	return result;

        }

        if(StringUtils.equals(authChannel, AuthChannelEnum.CMPAY0002.name())){
        	if(StringUtils.isBlank(terminalType)){
        		logger.info("缺少认证渠道必要参数！！！");
            	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_CODE);
            	result.put(Constants.MSG_KEY, "缺少认证渠道必要参数");
            	return result;
        	}
        }

        if(!InChannelEnum.contains(inchannel)){
        	logger.info("非法的渠道！！！");
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8805_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8805_MSG);
        	return result;
        }

        if(!AuthChannelEnum.contains(authChannel)){
        	logger.info("不支持的认证渠道！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8902_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8902_MSG);
        	return result;
        }

        if(!IdTypeEnum.contains(idType)){
        	logger.info("不支持的证件类型！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8903_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8903_MSG);
        	return result;
        }

        if(!SignCardTypeEnum.contains(cardType)){
        	logger.info("不支持的卡类型！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8904_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8904_MSG);
        	return result;
        }

        //2.判断该商户、渠道是否开通实名认证功能，后续配置信息存于redis中
        CmpayPayChannelExample cmpayPayChannelExample=new CmpayPayChannelExample();
        cmpayPayChannelExample.createCriteria().andMerchNoEqualTo(merchantId).andCodeEqualTo(authChannel);
        List<CmpayPayChannel> cpclist=cmpayPayChannelMapper.selectByExample(cmpayPayChannelExample);
        if(cpclist.size()<=0){
        	logger.info("商户[{}]不支持该渠道{}",merchantId,authChannel);
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8801_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8801_MSG);
        	return result;
        }

        CmpayPayChannel cmpayPayChannel=cpclist.get(0);
        if(!StringUtils.equals(Constants.ON, cmpayPayChannel.getCardAuthOpenStatus())){
        	logger.info("商户[{}]该认证渠道[{}]已关闭",merchantId,authChannel);
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8802_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8802_MSG);
        	return result;
        }

        //3.根据核心bankcode和支付渠道编码，查出支付渠道的bankcode(可选)
//        if(StringUtils.equals(authChannel, AuthChannelEnum.CMPAY0002.name())){
        	CmpaySuppBankMapExample cmpaySuppBankMapExample=new CmpaySuppBankMapExample();
        	cmpaySuppBankMapExample.createCriteria().andPayChannelCodeEqualTo(authChannel).andPayBankCodeEqualTo(bankCode);
            List<CmpaySuppBankMap> csbmlist=cmpaySuppBankMapMapper.selectByExample(cmpaySuppBankMapExample);
            if(csbmlist.size()<=0){
            	logger.info("渠道[{}]不支持该银行{}",authChannel,bankCode);
            	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8803_CODE);
            	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8803_MSG);
            	return result;
            }

            CmpaySuppBankMap cmpaySuppBankMap=csbmlist.get(0);
            if(!StringUtils.equals(Constants.ON, cmpaySuppBankMap.getStatus())){
            	logger.info("支付渠道[{}]该银行[{}]已关闭",authChannel,bankCode);
            	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8804_CODE);
            	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8804_MSG);
            	return result;
            }

//        }
            String pcBankCode=cmpaySuppBankMap.getPcBankCode();

        //4.调用pay-service-bank服务验证接口

         Map<String,String> map=payAuthService.doPayAuth(merchantId, userId, inchannel, AuthChannelEnum.valueOf(authChannel), cardNo, SignCardTypeEnum.valueOf(cardType), idNo, IdTypeEnum.valueOf(idType), name, bankMobile, pcBankCode, terminalType);

         logger.info("返回值为："+map.toString());
		return map;
	}

}
