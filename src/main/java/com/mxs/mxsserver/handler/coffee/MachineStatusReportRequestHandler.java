package com.mxs.mxsserver.handler.coffee;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.ErrorRecord;
import com.mxs.mxsserver.domain.Material;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.MachineStatusReportRequest;
import com.mxs.mxsserver.protocol.responce.coffee.MachineStatusReportResponce;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.ErrorRecordService;
import com.mxs.mxsserver.service.MaterialService;
import com.mxs.mxsserver.util.DateUtils;

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
	@Autowired
	private MaterialService mservice;
	@Autowired
	private ErrorRecordService errorservice;
	private static ErrorRecordService errorRecordService;
	private static CoffeeMachineService coffeeMachineService;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		materialService = mservice;
		errorRecordService = errorservice;
	}

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		MachineStatusReportRequest machineStatusReportRequest = (MachineStatusReportRequest) request;
		System.out.println(machineStatusReportRequest.getMachineStatusJson());
		System.out.println(machineStatusReportRequest.getTimestamp());
		//是否是 咖啡机的ID
		String venderName = machineStatusReportRequest.getLinkFrame().key;
		int status = machineStatusReportRequest.getStatus();

		//查询该咖啡机是否是新创建
		CoffeeMachine machineInfo = coffeeMachineService.getCoffeeMachineInfo(machineStatusReportRequest.getLinkFrame().key);
		boolean isrun = machineStatusReportRequest.isIs_running();
		String workerId = machineInfo.getEmployeeCode();
		
		if(null == machineInfo) {
			//初始化咖啡机信息
			machineInfo = new CoffeeMachine();
			machineInfo.setMachineId(venderName);
//			machineInfo.setMachineInfo(machineStatusReportRequest.getMachineStatusJson());
			machineInfo.setCreateTime(new Date());
//			machineInfo.setIs_running(machineStatusReportRequest.isIs_running());
//			machineInfo.setUpdateTime(new Date());
		}else {
			ErrorRecord error = null;
			if(!machineInfo.getIs_running() && isrun) {//前一次是停机状态，当前是重启，则计算这次错误的持续的时间
				//查询上一次的错误记录
				error = errorRecordService.queryErrorRecord(venderName);
				if(null != error) {
					Date start = error.getStartTime();
					Date end = new Date();
					Long duration = DateUtils.subtractForDate(start, end, 1000*60);//单位是分
					error.setDurationTime(duration);
					error.setEndTime(end);
					error.setSumError(error.getSumError() + 1);//错误总数加一
				}else {
					log.info("错误记录数据丢失，找不到前一次的停机记录");
				}
				//计算当前时间和上次停机时间的时间差
			}else if(machineInfo.getIs_running() && !isrun) {//前一次正常，当前是停机，则新建错误记录
				error = new ErrorRecord();
				error.setMachineId(venderName);
				error.setStartTime(new Date());
				error.setType(2);//停机错误，当前无警报处理
				error.setWorkerId(workerId);
			}
			if(error != null)
				errorRecordService.addErrorRecord(error);
		}
		if(!isrun) {//停机，则设置停机的时间
			machineInfo.setDownTime(new Date());
		}
		machineInfo.setStatus(status);
		machineInfo.setIs_running(isrun);
		machineInfo.setUpdateTime(new Date());
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
