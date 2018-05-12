package com.mxs.mxsserver.handler;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.handler.coffee.MachineStatusReportRequestHandler;
import com.mxs.mxsserver.protocol.request.LoginRequest;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.LoginResponce;
import com.mxs.mxsserver.service.LoginInfoService;
import com.mxs.mxsserver.util.Enums.Login;
import com.mxs.mxsserver.util.ResponseCode;

//登录处理
@Component
public class LoginRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(LoginRequestHandler.class);
	@Autowired
	private LoginInfoService loginService;
	private static LoginInfoService loginInfoService;
	@PostConstruct
	public void init() {
		loginInfoService = loginService;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		short status = 0;
		// core.cancelRequestRetryTimer(request.getLinkFrame().serialId);
		LoginRequest loginRequest = (LoginRequest) request;
		LoginResponce loginResponce = new LoginResponce(loginRequest.getUid());
		loginResponce.getLinkFrame().serialId = loginRequest.getLinkFrame().serialId;
		String userName = loginRequest.getUid();
		String password = loginRequest.getPassword();
		/* System.out.println(MD5.md5(loginRequest.getPassword())); */
		Login result = loginInfoService.queryUserInfo(userName, password);
		log.info(loginRequest.getUid() + "----"+loginRequest.getPassword());
		log.info(result + "----1234556789");
		if (result == Login.USER_NOT_EXIST) {
			loginResponce.getLinkFrame().resCode = ResponseCode.RES_ENONEXIST;
		} else if (result == Login.PASSWORD_ERROR) {
			loginResponce.getLinkFrame().resCode = ResponseCode.RES_EUIDPASS;
		} else if (result == Login.LOGIN_SSUCCESS){
			loginResponce.getLinkFrame().resCode = ResponseCode.RES_SUCCESS;
		}else {
			loginResponce.getLinkFrame().resCode = ResponseCode.RES_ENONEXIST;
		}

		loginResponce.setsessionId(loginRequest.getUid());
		loginResponce.setstatus(status);
		loginResponce.setvendorname(loginRequest.getUid());

		ctx.getChannel().write(loginResponce);

	}

}
