package com.mxs.mxsserver.service;

import com.mxs.mxsserver.util.Enums.Login;

public interface LoginInfoService {
	Login queryUserInfo(String venderName, String venderPassword);
}
