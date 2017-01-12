//package com.cmpay.gateway.exception;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// * @author gengkangkang
// * @E-mail gengkangkang@cm-inv.com
// *
// * 2016年11月10日 下午2:31:36
// *
// *  异常统一处理
// *
// */
//public class ExceptionResolver implements HandlerExceptionResolver {
//	private Logger logger=Logger.getLogger(ExceptionResolver.class);
//
//	@Override
//	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object handler,
//			Exception ex) {
//
//            try {
//                res.setContentType("text/text;charset=UTF-8");
//                res.getWriter().write("system error");
//            } catch (IOException e) {
//            	logger.error("系统异常:", e);
//
//            }
//
//        return null;
//	}
//
//}
