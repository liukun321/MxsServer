package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_BAR_CODE
		+ "" })
public class PayBarCodeRequest extends SingleRequest {
	//咖啡产品信息
	@PackIndex(1)
	private String coffeeId;
	//支付方式
//	@PackIndex(2)
//	private String provider;
	//购买者的条形码信息
	@PackIndex(2)
	private String authCode;
	//咖啡机编号
	@PackIndex(3)
	private String machineId;
//	//原始价钱
	@PackIndex(4)
	private String discount;
	
	/**
     * 冷热饮
     */
	@PackIndex(5)
    private boolean isHot;

    /**
     * 糖份
     * 0 1 2 3 4 
     */
	@PackIndex(6)
    private int sugar;
	
	@PackIndex(7)
    private String ip;
	
	
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeId = unpack.popVarstr();
//		provider = unpack.popVarstr();
		authCode = unpack.popVarstr();
		machineId = unpack.popVarstr();
		discount = unpack.popVarstr();
		isHot = unpack.popBoolean();
		sugar = unpack.popInt();
		ip = unpack.popVarstr();
        return null;
    }


	public String getCoffeeId() {
		return coffeeId;
	}


//	public String getProvider() {
//		return provider;
//	}


	public String getAuthCode() {
		return authCode;
	}


	public String getMachineId() {
		return machineId;
	}


	public String getDiscount() {
		return discount;
	}


	public boolean isHot() {
		return isHot;
	}


	public int getSugar() {
		return sugar;
	}


	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}


//	public void setProvider(String provider) {
//		this.provider = provider;
//	}


	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}


	public void setSugar(int sugar) {
		this.sugar = sugar;
	}
	

}
