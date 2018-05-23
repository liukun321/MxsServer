package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.AlarmForMaterialResquest;
import com.mxs.mxsserver.protocol.responce.coffee.AlarmForMaterialResponse;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.service.JPushService;

//更新咖啡机的状态
@Component
public class AlarmForMaterialResquestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(AlarmForMaterialResquestHandler.class);
	@Autowired
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	
	@Autowired
	private EmployeeService eservice;
	private static EmployeeService employeeService;
	
	@Autowired
	private JPushService jservice;
	private static JPushService jPushService;
	
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		employeeService = eservice;
		jPushService = jservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		AlarmForMaterialResquest alarmForMaterialRequest = (AlarmForMaterialResquest) request;
		
		String machineId = alarmForMaterialRequest.getLinkFrame().key;
		AlarmForMaterialResponse alarmForMaterialResponse = new AlarmForMaterialResponse(machineId);
		// 物料报警处理
		int num = alarmForMaterialRequest.getBoxNumber();
		double value = alarmForMaterialRequest.getValue();
		CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineId);
		String employeeCode = coffeeMachine.getEmployeeCode();
		
		//找到运营人员对应的设备标识
		Employee employee = employeeService.queryEmployeeByPhone(employeeCode);
		
		//推送该信息到该员工的APP，咖啡机ID和哪个物料盒缺料
		
//		int result = jPushService.sendToRegistrationId(registrationId, notification_alert, notification_title, extrasparam)
//		if(0 == result) {
//			log.info("推送失败");
//		}else if(1 == result) {
//			log.info("推送成功");
//		}
		alarmForMaterialResponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(alarmForMaterialResponse);

	}

}
