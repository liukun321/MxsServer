package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;
/**
 * 物料报警响应
 * @author liukun
 *
 */
public class AlarmForMaterialResponse extends Responce {

	public AlarmForMaterialResponse(String uid) {
		super(uid);
	}

	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        return pack;
    }
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ALARM_MATERIAL;
	}

}
