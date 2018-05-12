package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.UPDATE_STOCK
        + "" })
public class UpdateStockRequest extends SingleRequest {
	@PackIndex(1)
	private String inventory;
	
   @Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
	   inventory = unpack.popVarstr();
	   return null;
    }
    public String getInventory(){
    	return this.inventory;
    }
    public void setInventory(String inventory){
    	this.inventory = inventory;
    }

}
