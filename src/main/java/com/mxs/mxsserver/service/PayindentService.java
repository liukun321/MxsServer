package com.mxs.mxsserver.service;

import java.util.List;

import com.mxs.mxsserver.domain.Payindent;

public interface PayindentService {
	Payindent addPayindent(Payindent payindent);
	
	boolean deletePayindent(String indentId);
	
	Payindent queryPayindentById(String indentId);
	
	List<Payindent> queryOrderByStatus(Integer status);
	
	Double querySumprice(Integer status, Integer payMethod);
}
