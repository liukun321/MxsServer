package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachineAddress;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.UpdateMachineAddressRequest;
import com.mxs.mxsserver.protocol.responce.coffee.UpdateMachineAddressResponce;
import com.mxs.mxsserver.service.MachineAddressService;
import com.mxs.mxsserver.util.StringUtils;

//咖啡机地理位置信息更新
@Component
public class UpdateMachineAddressHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(UpdateMachineAddressHandler.class);
	@Autowired
	private MachineAddressService mervice;
	private static MachineAddressService machineAddressService;
	@PostConstruct
	public void init() {
		machineAddressService = mervice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("------开始更新地理位置-------");
		UpdateMachineAddressRequest updateMachineAddressRequest = (UpdateMachineAddressRequest) request;
		log.info("咖啡机用户=" + updateMachineAddressRequest.getLinkFrame().key);
		log.info(updateMachineAddressRequest.getMachineId());
		
		String machineId = updateMachineAddressRequest.getMachineId();
		CoffeeMachineAddress machineAddress = null;
		if(!StringUtils.isNull(machineId)) {
			machineAddress = machineAddressService.queryMachineAddressById(machineId);
			if(null == machineAddress) {
				machineAddress = new CoffeeMachineAddress();
			}
			BeanUtils.copyProperties(updateMachineAddressRequest, machineAddress);
			machineAddress = machineAddressService.updateMachineAddress(machineAddress);
		}
		UpdateMachineAddressResponce updateStockResponce = new UpdateMachineAddressResponce(updateMachineAddressRequest.getLinkFrame().key);
		updateStockResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		
		if (null == machineAddress)
			updateStockResponce.getLinkFrame().resCode = 0;
		ctx.getChannel().write(updateStockResponce);
	}

}
