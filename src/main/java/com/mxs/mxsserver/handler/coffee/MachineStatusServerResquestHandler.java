package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.MachineStatu;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.MachineStatusServerRequest;
import com.mxs.mxsserver.protocol.responce.coffee.MachineStatusServerResponce;
import com.mxs.mxsserver.service.MachineStatusService;

//更新咖啡机的状态
@Component
public class MachineStatusServerResquestHandler extends RequestHandler {

	@Autowired
	private MachineStatusService service;
	private static MachineStatusService machineStatusService;
	@PostConstruct
	public void init() {
		machineStatusService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		MachineStatusServerRequest machineStatusServerRequest = new MachineStatusServerRequest();
		MachineStatusServerResponce machineStatusServerResponce = new MachineStatusServerResponce(
				request.getLinkFrame().key);
		System.out.println(machineStatusServerRequest.getMachineInfo());
		System.out.println(machineStatusServerRequest.getTimestamp());

//		int flag = database.insertinfo(machineStatusServerRequest.getLinkFrame().key,
//				machineStatusServerRequest.getTimestamp(), machineStatusServerRequest.getMachineInfo());
		
		MachineStatu machineStatus = new MachineStatu();
		machineStatus.setMachineId(machineStatusServerRequest.getLinkFrame().key);
		machineStatus.setTimestamps(machineStatusServerRequest.getTimestamp());
		machineStatus.setStatusjson(machineStatusServerRequest.getMachineInfo());
		
		machineStatusService.addMachineStatus(machineStatus);
		
		machineStatusServerResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(machineStatusServerResponce);

	}

}
