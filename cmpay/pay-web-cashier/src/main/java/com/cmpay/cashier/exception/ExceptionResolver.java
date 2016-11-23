package com.cmpay.cashier.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cmpay.common.exception.BizException;
import com.cmpay.common.util.JsonUtils;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月10日 下午2:31:36
 *
 *  异常统一处理
 *
 */
public class ExceptionResolver implements HandlerExceptionResolver {
	private Logger logger=Logger.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object handler,
			Exception ex) {
        if (BizException.class.isAssignableFrom(ex.getClass())) {//如果是业务异常类
            BizException bizException = (BizException) ex;

            try {
                res.setContentType("text/text;charset=UTF-8");
                JsonUtils.responseJson(res, bizException.getMsg());


                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorMsg", bizException.getMsg());//将错误信息传递给view
                return new ModelAndView("exception/exception",map);
            } catch (IOException e) {
            	logger.error("系统异常:", e);

            }
        }
        return null;
	}

}
