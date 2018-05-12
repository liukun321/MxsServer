package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ROLL_BACK_CART
		+ "" })
public class RollbackOrderRequest extends Request {
	@PackIndex(1)
	private String payIndent;//订单编号
	@PackIndex(2)
	private String coffeeIndents;//订单详情
	@PackIndex(3)
	private String reason;//退款原因

	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		payIndent = unpack.popVarstr();
		coffeeIndents = unpack.popVarstr();
		reason = unpack.popVarstr();
		return null;
	}
	public String getCoffeeIndents() {
		return coffeeIndents;
	}
	public void setCoffeeIndents(String coffeeIndents) {
		this.coffeeIndents = coffeeIndents;
	}
	public String getPayIndent() {
		return payIndent;
	}
	public void setPayIndent(String payIndent) {
		this.payIndent = payIndent;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
