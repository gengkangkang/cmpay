package com.cmpay.service.bank.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.pay.service.jytpay.service.JYTAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.Constants;
import com.cmpay.service.bank.dao.CmpayAuthRecordMapper;
import com.cmpay.service.bank.dao.CmpayChannelConfigMapper;
import com.cmpay.service.bank.model.CmpayAuthRecord;
import com.cmpay.service.bank.model.CmpayChannelConfig;
import com.cmpay.service.bank.model.CmpayChannelConfigExample;
import com.cmpay.service.bank.service.PayAuthService;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;
import com.cmpay.service.chinapay.service.ChinapayService;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月28日 上午11:26:43
 *
 */
@Service
public class PayAuthServiceImpl implements PayAuthService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpayAuthRecordMapper cmpayAuthRecordMapper;
    @Autowired
	CmpayChannelConfigMapper cmpayChannelConfigMapper;

    @Autowired
    ChinapayService chinapayService;
    @Autowired
    JYTAuthService jYTAuthService;

	@Override
	public Map<String, String> doPayAuth(String merchantId,String userId,String inchannel,AuthChannelEnum authChannel,String cardNo, SignCardTypeEnum cardType, String idNo, IdTypeEnum idType,
			String name, String bankMobile, String bankCode, String terminalType) {
		logger.info("[{}]开始卡认证，inchannel=[{}],authChannel=[{}],cardNO=[{}]",userId,inchannel,authChannel,cardNo);
		String code=Constants.FAILED_CODE;
		String msg="";
		Map<String,String> result=new HashMap<String,String>();
       //插入认证流水
		CmpayAuthRecord cmpayAuthRecord=new CmpayAuthRecord();
		cmpayAuthRecord.setMerchantid(merchantId);
		cmpayAuthRecord.setId(CmpayUtils.getUUID());
		cmpayAuthRecord.setUserid(userId);
		cmpayAuthRecord.setInchannel(inchannel);
		cmpayAuthRecord.setAuthchannel(authChannel.name());
		cmpayAuthRecord.setCardno(cardNo);
		cmpayAuthRecord.setCardtype(cardType.name());
		cmpayAuthRecord.setIdno(idNo);
		cmpayAuthRecord.setIdtype(idType.name());
		cmpayAuthRecord.setName(name);
		cmpayAuthRecord.setBankmobile(bankMobile);
		cmpayAuthRecord.setCreateTime(new Date());
		cmpayAuthRecord.setVersion(0);
		if(StringUtils.equals(AuthChannelEnum.CMPAY0002.name(), authChannel.name())){
			//jyt
			cmpayAuthRecord.setBankcode(bankCode);
			cmpayAuthRecord.setTerminaltype(terminalType);
		}

		cmpayAuthRecord.setStatus(PayStatusEnum.DEALING.name());
		cmpayAuthRecordMapper.insert(cmpayAuthRecord);

		//查配置信息，后续放入redis
		CmpayChannelConfigExample cccCondition=new CmpayChannelConfigExample();
		cccCondition.createCriteria().andMerNoEqualTo(merchantId).andPaychannelNoEqualTo(authChannel.name());
		List<CmpayChannelConfig> cmpayChannelConfiglist=cmpayChannelConfigMapper.selectByExample(cccCondition);
		if(cmpayChannelConfiglist.size()<=0){
			logger.info("该商户缺少配置信息！！！");
			result.put(Constants.CODE_KEY, Constants.FAILED_CODE);
			result.put(Constants.MSG_KEY, "商户缺少配置信息");
             return result;
		}

		CmpayChannelConfig cmpayChannelConfig=cmpayChannelConfiglist.get(0);
		logger.debug("paychannel config-->[{}]",cmpayChannelConfig.toString());

		//2.根据authChannel选择认证渠道
          switch(authChannel){
             case CMPAY0001:
                  //银联验卡  bgAuth(String appSysId,String authPrivateKey,String cardNo, IdType idType, String idNo,SignCardTypeDef cardType, String custName, String cardPhone);
            	 CpAuthBgRespDef resp=chinapayService.bgAuth(cmpayChannelConfig.getAppid(), cmpayChannelConfig.getAppsectet(), cardNo, idType, idNo, cardType, name, bankMobile);
            	 if(resp==null){
            		 logger.info("银联无返回对象!");
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 code=Constants.FAILED_CODE;
            		 msg="银联无返回对象";
            	 }else if(StringUtils.equals("0000", resp.getRespCode())){
            		 logger.info("银联验证成功!");
            		 code=Constants.SUCCESS_CODE;
            		 msg="验证成功";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.SUCC.name());
            	 }else{
            		 logger.info("银联验卡失败，resp=[{}]",resp.toString());
            		 code=Constants.FAILED_CODE;
            		 msg="验证失败";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 cmpayAuthRecord.setRemark(resp.getRespMsg());
            	 }
//            	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);


        	  break;
             case CMPAY0002:
                  //jyt      String merNo,String pubKey,String privKey,String privPwd,
            	 Map<String,String> respmap=jYTAuthService.JYTAuth(cmpayChannelConfig.getThirdMerid(),cmpayChannelConfig.getRsapubkey(),cmpayChannelConfig.getRsaprikey(),cmpayChannelConfig.getAppsectet(),cardNo, bankCode, idNo, name, terminalType, cardType, bankMobile, idType);

            	 if(respmap==null){
            		 logger.info("金运通无返回对象!");
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 code=Constants.FAILED_CODE;
            		 msg="金运通无返回对象";
            	 }else if(StringUtils.equals("0000", respmap.get("code"))){
            		 logger.info("金运通验证成功!");
            		 code=Constants.SUCCESS_CODE;
            		 msg="验证成功";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.SUCC.name());
            	 }else{
            		 logger.info("金运通验卡失败，resp=[{}]",respmap.toString());
            		 code=Constants.FAILED_CODE;
            		 msg="验证失败";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 cmpayAuthRecord.setRemark(respmap.get("msg"));
            	 }
//            	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);

           	  break;

             default:
            	 logger.info("不支持的渠道");
            	 Constants.CODE_KEY=Constants.FAILED_CODE;
            	 Constants.MSG_KEY="不支持的渠道";
            	 break;
          }

     	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);


       result.put(Constants.CODE_KEY, code);
       result.put(Constants.MSG_KEY, msg);

		return result;
	}

}
