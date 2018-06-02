package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.MobilePay;
import com.mxs.mxsserver.repository.MobilePayRepository;
import com.mxs.mxsserver.service.MobilePayService;

@Service
public class MobilePayServiceImpl implements MobilePayService {

	private MobilePayRepository mobilePayRepository;
	
	public MobilePayServiceImpl(MobilePayRepository mobilePayRepository) {
		super();
		this.mobilePayRepository = mobilePayRepository;
	}

	@Override
	public MobilePay queryMobilePay(String indentId) {
		return mobilePayRepository.findOne(indentId);
	}


}
