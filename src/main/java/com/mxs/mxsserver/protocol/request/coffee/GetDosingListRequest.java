package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.marshal.ArrayMable;
import com.mxs.mxsserver.protocol.marshal.Property;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.GET_DOSING
		+ "" })

public class GetDosingListRequest extends Request {

    
	
	public Unpack unpackBody(Unpack unpack) throws Exception {
        /*setCoffeeDosingList(new ArrayMable(Property.class));
        coffeeDosingList.unmarshal(unpack);*/
		return null;
	}

    /*public ArrayMable getCoffeeDosingList() {
        return coffeeDosingList;
    }*/

    /*public void setCoffeeDosingList(ArrayMable coffeeDosingList) {
        this.coffeeDosingList = coffeeDosingList;
    }*/
}
