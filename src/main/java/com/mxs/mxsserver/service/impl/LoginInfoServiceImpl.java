package com.mxs.mxsserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.LoginInfo;
import com.mxs.mxsserver.repository.LoginInfoRepository;
import com.mxs.mxsserver.service.LoginInfoService;
import com.mxs.mxsserver.util.Enums.Login;
@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	private final Logger log = LoggerFactory.getLogger(LoginInfoServiceImpl.class);
	
	private LoginInfoRepository loginInfoRepository;
	
	public LoginInfoServiceImpl(LoginInfoRepository loginInfoRepository) {
		super();
		this.loginInfoRepository = loginInfoRepository;
	}

	@Override
	public Login queryUserInfo(String venderName, String venderPassword) {
		try {
			LoginInfo loginInfo = loginInfoRepository.findByVenderName(venderName);
			if (null == loginInfo) {
				return Login.USER_NOT_EXIST;
			} else if (venderPassword.equals(loginInfo.getVenderPassword())) {//MD5.md5(result)
				return Login.LOGIN_SSUCCESS;
			} else {
				return Login.PASSWORD_ERROR;
			}
		}catch(Exception e) {
			log.info(e.getMessage());
			return Login.LOGIN_EXCEPTION;
		}
		
	}

}
