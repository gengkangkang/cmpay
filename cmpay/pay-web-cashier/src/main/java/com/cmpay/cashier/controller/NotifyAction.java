package com.cmpay.cashier.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmpay.common.util.WXConstants;
import com.cmpay.weixin.service.PaymentService;
import com.cmpay.weixin.utils.WeiXinPayUtils;


/**
 * 接受通知类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月18日 下午4:38:23
 *
 */
@Controller
@RequestMapping("/notify")
public class NotifyAction {
    private  Logger logger = LoggerFactory.getLogger(NotifyAction.class);
	@Autowired
	private PaymentService paymentService;

    @RequestMapping("/WEIXIN")
    public void wxnotify(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

    	   System.out.println(new Date()+"---------接受到微信支付异步通知请求--------");
           Map<String , String> notifyMap = new HashMap<String , String >();
           try{
            InputStream inputStream = httpServletRequest.getInputStream();
            notifyMap = WeiXinPayUtils.parseXml(inputStream);

            String result = paymentService.acceptWXNotify(notifyMap);
            httpServletResponse.setContentType("text/xml");

            httpServletResponse.getWriter().print(result);
           }catch(Exception e){
            logger.error("接受微信通知发生异常！！！！！！！");
            try {
				httpServletResponse.getWriter().print(WXConstants.WX_RETURN_FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
           }

    }

}
