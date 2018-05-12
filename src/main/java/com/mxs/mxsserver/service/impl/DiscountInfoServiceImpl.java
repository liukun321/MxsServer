package com.mxs.mxsserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.DiscountInfo;
import com.mxs.mxsserver.repository.DiscountInfoRepository;
import com.mxs.mxsserver.service.DiscountInfoService;
@Service
public class DiscountInfoServiceImpl implements DiscountInfoService {
	private final Logger log = LoggerFactory.getLogger(DiscountInfoServiceImpl.class);
	private DiscountInfoRepository discountInfoRepository;
	
	public DiscountInfoServiceImpl(DiscountInfoRepository discountInfoRepository) {
		super();
		this.discountInfoRepository = discountInfoRepository;
	}


	@Override
	public DiscountInfo queryDiscountInfo(String venderName) {
		return discountInfoRepository.findByVenderName(venderName);
	}

}
