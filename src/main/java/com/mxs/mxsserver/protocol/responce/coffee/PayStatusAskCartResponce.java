package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;
/**
 * 查询支付状态
 * @author liukun
 *
 */
public class PayStatusAskCartResponce extends Responce {
	private int payStatus;
	public PayStatusAskCartResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putInt(payStatus);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ASK_PAY_STATUS_CART;
	}


	public int getPayStatus() {
		return payStatus;
	}


	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	

}
