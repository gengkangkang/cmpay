package com.cmpay.cashier.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cmpay.cashier.exception.TradeBizException;
import com.cmpay.common.enums.TransTypeEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.weixin.service.PaymentService;
import com.cmpay.weixin.vo.PayPageShowVo;
import com.cmpay.weixin.vo.QueryResult;
import com.cmpay.weixin.vo.ScanPayResultVo;

/**
 * 收银台相关类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月17日 下午1:54:23
 *
 */
@Controller
@RequestMapping("/payment")
public class PayAction {
	private Logger logger=LoggerFactory.getLogger(PayAction.class);

	@Autowired
	private PaymentService paymentService;
	@Value("#{env['order_query_url']}")
	private String order_query_url;


    @RequestMapping("/pay.action")
    public String sayHello(HttpServletRequest request,Model model){
    	String ip=CmpayUtils.getIpAddr(request);
    	logger.info(ip+",调用收银台================");
        String orig_orderId=request.getParameter("orig_orderId");
        String amount=request.getParameter("amount");
        String transType=request.getParameter("transType");
        String proName=request.getParameter("proName");
        String mId=request.getParameter("mId");
        String inchannel=request.getParameter("inchannel");
        String uId=request.getParameter("uId");
        String token=request.getParameter("token");
       if(StringUtils.isBlank(orig_orderId)||StringUtils.isBlank(amount)||StringUtils.isBlank(transType)||StringUtils.isBlank(proName)
    		   ||StringUtils.isBlank(mId)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(uId)||StringUtils.isBlank(token)){
           throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"请检查参数是否完整！！！");

       }

      TransTypeEnum transTypeEnum=TransTypeEnum.getByTransCode(transType);
      if(transTypeEnum==null){
          throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"请检查参数是否正确！！！");
      }

       //1.根据userId,token调用用户中心检测用户状态
         //暂时跳过

       //2.根据商户号查询配置信息，看该商户支持哪几种支付方式
         //暂时跳过

       //3.组织参数，跳转到收银台页面
      BigDecimal orderPrice = BigDecimal.valueOf(Double.valueOf(amount));
      PayPageShowVo payPageShowVo=paymentService.createPreOrder(mId, inchannel, uId, transType, orig_orderId, orderPrice, ip, proName);

      model.addAttribute("payGateWayPageShowVo",payPageShowVo);//支付网关展示数据
      return "gateway";
    }

    @RequestMapping("/toPay/{orderNo}/{proName}")
    public String toPay(@PathVariable("proName")String proName , @PathVariable("orderNo")String orderNo ,Model model){

        ScanPayResultVo scanPayResultVo = paymentService.unifiedorder(orderNo, proName);

             model.addAttribute("codeUrl",scanPayResultVo.getCodeUrl());//支付二维码

            model.addAttribute("queryUrl", order_query_url + "?orderId=" + orderNo);
            model.addAttribute("productName",scanPayResultVo.getProductName());//产品名称
            model.addAttribute("orderPrice",scanPayResultVo.getOrderAmount());//订单价格
            return "weixinPayScanPay";

    }

    @RequestMapping("/orderQuery")
    @ResponseBody
    public String orderQuery(@RequestParam("orderId") String orderId){
    	logger.info("订单号[{}]查询订单状态",orderId);
    	QueryResult queryResult=paymentService.OrderQuery(orderId);
    	String res=JSON.toJSONString(queryResult);
    	logger.info("返回内容[{}],json[{}]",queryResult.toString(),res);
    	return res;
    }

}
