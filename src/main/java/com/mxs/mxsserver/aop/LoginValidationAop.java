package com.mxs.mxsserver.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.util.MxsConstants;
import com.mxs.mxsserver.util.StringUtils;

/**
 * 登陆后的请求验证
 * @author liukun
 *
 */
@Configuration
@Aspect
public class LoginValidationAop {
	private static Logger log = LoggerFactory.getLogger(LoginValidationAop.class);
	private static Map<String, HttpSession> sessionMap =  new HashMap();
	
	//登陆拦截，存储登陆的会话
//	@Around(value = "execution (* com.mxs.mxsserver.controller.EmployeeController.login(..))")
//	public Object loginRequest(ProceedingJoinPoint pro) throws Throwable {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		HttpSession session = request.getSession();
//		String sessionId = session.getId();
//		HttpSession oldSession = sessionMap.get(sessionId);
//		if(null == oldSession) {
//			//不存在，则是初次登陆
//			sessionMap.put(session.getId(), session);
//		}
//	}
	
	
	
	@Around(value = "execution (* com.mxs.mxsserver.controller.EmployeeController.user*(..))")
	public Object validaitonRequest(ProceedingJoinPoint pro) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String token = "", phone = "";
//		String sessionId = session.getId();
//		HttpSession oldSession = sessionMap.get(sessionId);
//		if(null == oldSession) {
//			//不存在，则是初次登陆
////			sessionMap.put(session.getId(), session);
//			return ResultBean.error(MxsConstants.CODE1, phone + "登陆失效，请重新登陆"); 
//		}else {
			token = (String) session.getAttribute("tokens");
			phone = (String) session.getAttribute("phone");
			log.info(session.getId() + " --- " + token + " --- " + phone);
			
			String new_tokens = request.getHeader("token");
			log.info("token = " + token + " new_tokens = " + new_tokens);
			if(token.equals(new_tokens) && !StringUtils.isNull(token)) {
				Object proceed = pro.proceed();
				return proceed;
			}else {
				return ResultBean.error(MxsConstants.CODE1, phone + "登陆失效，请重新登陆");
			}
//		}
	}
}
