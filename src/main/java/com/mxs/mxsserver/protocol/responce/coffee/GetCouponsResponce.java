package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;
/**
 * 获取优惠券的响应
 * @author liukun
 *
 */
public class GetCouponsResponce extends Responce {
	private String favorable;

	public GetCouponsResponce(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putVarstr(favorable);
		return p;
	}
	
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_COUPONS;
	}

	public String getFavorable() {
		return favorable;
	}

	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}

	
}
