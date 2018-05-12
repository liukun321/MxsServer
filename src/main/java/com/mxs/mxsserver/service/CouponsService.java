package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.Coupons;

public interface CouponsService {
	Coupons queryCouponsByCode(String couponCode);
	
	Coupons addCoupons(Coupons coupons);
}
