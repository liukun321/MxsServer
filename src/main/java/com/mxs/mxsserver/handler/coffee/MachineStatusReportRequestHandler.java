package com.mxs.mxsserver.handler.coffee;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Material;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.MachineStatusReportRequest;
import com.mxs.mxsserver.protocol.responce.coffee.MachineStatusReportResponce;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.MaterialService;

/**
 * 11 咖啡机状态报告
 * @author liukun
 *初始化咖啡机的状态信息或者更新状态
 */
@Component
public class MachineStatusReportRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(MachineStatusReportRequestHandler.class);
	@Autowired
	private CoffeeMachineService service;
	private MaterialService mservice;
	private static CoffeeMachineService coffeeMachineService;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		materialService = mservice;
	}

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		MachineStatusReportRequest machineStatusReportRequest = (MachineStatusReportRequest) request;
		System.out.println(machineStatusReportRequest.getMachineStatusJson());
		System.out.println(machineStatusReportRequest.getTimestamp());
		//是否是 咖啡机的ID
		String venderName = machineStatusReportRequest.getLinkFrame().key;

//		int flag = database.insertstatus(machineStatusReportRequest.getLinkFrame().key,
//				machineStatusReportRequest.getTimestamp(), machineStatusReportRequest.getMachineStatusJson());
		//查询该咖啡机是否是新创建
		CoffeeMachine machineInfo = coffeeMachineService.getCoffeeMachineInfo(machineStatusReportRequest.getLinkFrame().key);
//		CoffeeMachine machineInfo = null;
		if(null == machineInfo) {
			//初始化咖啡机信息
			machineInfo = new CoffeeMachine();
			machineInfo.setMachineId(venderName);
			machineInfo.setMachineInfo(machineStatusReportRequest.getMachineStatusJson());
			machineInfo.setCreateTime(new Date());
			machineInfo.setStatus(0);
			machineInfo.setIs_running(machineStatusReportRequest.isIs_running());
			machineInfo.setUpdateTime(new Date());
		}else {
			//更新咖啡机信息
//			machineInfo = cm;
			machineInfo.setIs_running(machineStatusReportRequest.isIs_running());
			machineInfo.setUpdateTime(new Date());
		}
		// TODO 获取咖啡机物料状态信息
		log.info(venderName + "咖啡机物料信息：" + machineStatusReportRequest.getMachineStatusJson());
		Material material = materialService.queryMaterial(venderName);
		if(null == material) {
			material = new Material();
			material.setMachineId(venderName);
		}
		// ？？机器状态的json串是否与物料表的字段对应
		material = JSON.parseObject(machineStatusReportRequest.getMachineStatusJson(), Material.class);
		//更新咖啡机物料信息
		materialService.updateMaterial(material);
		//更新咖啡机状态
		coffeeMachineService.addCoffeeMachine(machineInfo);
		
		MachineStatusReportResponce machineStatusReportResponce = new MachineStatusReportResponce(
				request.getLinkFrame().key);
		machineStatusReportResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(machineStatusReportResponce);
	}
}
