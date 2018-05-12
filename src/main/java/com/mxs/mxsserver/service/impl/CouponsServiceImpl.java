package com.mxs.mxsserver.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.Coupons;
import com.mxs.mxsserver.repository.CouponsRepository;
import com.mxs.mxsserver.service.CouponsService;
@Service
public class CouponsServiceImpl implements CouponsService {
	
	private CouponsRepository couponsRepository;
	
	public CouponsServiceImpl(CouponsRepository couponsRepository) {
		super();
		this.couponsRepository = couponsRepository;
	}

	@Override
	public Coupons queryCouponsByCode(String couponCode) {
		Date date = new Date();
		return couponsRepository.findByCouponCodeAndEndTimeAfter(couponCode, date);
	}

	@Override
	public Coupons addCoupons(Coupons coupons) {
		return couponsRepository.save(coupons);
	}

}
