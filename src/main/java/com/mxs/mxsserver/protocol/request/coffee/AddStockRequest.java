package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ADD_STOCK
        + "" })
public class AddStockRequest extends Request {
	@PackIndex(0)
	private int userID;
	@PackIndex(1)
	private String inventory;
	
	
    @Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
    	userID = unpack.popInt();
    	inventory = unpack.popVarstr();
        return null;
    }
    public void setInventory(String inventory) {
		this.inventory = inventory;
	}
    public String getInventory() {
		return inventory;
	}
    public void setUserID(int userID) {
		this.userID = userID;
	}
    public int getUserID() {
		return userID;
	}

}
