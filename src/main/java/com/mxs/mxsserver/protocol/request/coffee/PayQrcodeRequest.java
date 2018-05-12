package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_QRCODE
		+ "" })
public class PayQrcodeRequest extends SingleRequest {
	@PackIndex(0)
	private int coffeeId;
	@PackIndex(1)
	private String dosing;
	@PackIndex(2)
	private short provider;
//	@PackIndex(3)
//	private String buyerId;
	
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeId = unpack.popInt();
		dosing = unpack.popVarstr();
		provider = unpack.popShort();
        return null;
    }
	public void setCoffeeId(int coffeeId) {
		this.coffeeId = coffeeId;
	}
	public int getCoffeeId() {
		return coffeeId;
	}
	public void setDosing(String dosing) {
		this.dosing = dosing;
	}
	public String getDosing() {
		return dosing;
	}
	public void setProvider(short provider) {
		this.provider = provider;
	}
	public short getProvider() {
		return provider;
	}

}
