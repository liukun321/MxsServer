package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.CoffeeMaterial;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.AlarmForMaterialResquest;
import com.mxs.mxsserver.protocol.responce.coffee.AlarmForMaterialResponse;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.CoffeeMaterialService;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.service.JPushService;
import com.mxs.mxsserver.util.MaterialEnum;
import com.mxs.mxsserver.util.MxsConstants;

/**
 * 咖啡机物料预警信息请求
 * @author liukun
 *咖啡机物料预警：
 *1 更新咖啡机的状态
 *2 更新咖啡机相应物料的状态
 *2 向运维人员推送报警信息
 */
@Component
public class AlarmForMaterialResquestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(AlarmForMaterialResquestHandler.class);
	@Autowired
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	
	@Autowired
	private CoffeeMaterialService cervice;
	private static CoffeeMaterialService coffeeMaterialService;
	
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
		coffeeMaterialService = cervice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("--------------咖啡机预警请求------------------");
		AlarmForMaterialResquest alarmForMaterialRequest = (AlarmForMaterialResquest) request;
		
		String machineId = alarmForMaterialRequest.getLinkFrame().key;
		AlarmForMaterialResponse alarmForMaterialResponse = new AlarmForMaterialResponse(machineId);
		// 物料报警处理
		int num = alarmForMaterialRequest.getBoxNumber();
		double value = alarmForMaterialRequest.getValue();
		int type = alarmForMaterialRequest.getType();
		CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineId);
		coffeeMachine.setStatus(type);
		coffeeMachineService.addCoffeeMachine(coffeeMachine);
		String employeeCode = coffeeMachine.getEmployeeCode();
		
		//TODO 添加物料报警信息
		CoffeeMaterial material = coffeeMaterialService.queryMaterialByIdAndNumber(machineId, num);
		material.setStatus(type);
		coffeeMaterialService.addMaterial(material);
		
		//找到运营人员对应的设备标识,字段暂时还没有
		Employee employee = employeeService.queryEmployeeByPhone(employeeCode);
		String registrationId = employee.getWorkerId();
		//TODO　推送消息模板 哪台咖啡机/几号料盒，什么物料，缺料，剩余量是多少，或者百分比
		String notification_alert = "咖啡机" + machineId + "的" + num + "料盒物料缺料， 请及时处理(" + value + ")";
		String notification_title = "咖啡机物料警报";
		//推送该信息到该员工的APP，咖啡机ID和哪个物料盒缺料
		
		int result = jPushService.sendToRegistrationId(registrationId, notification_alert, notification_title, "");
		if(0 == result) {
			log.info(notification_alert + "推送失败");
		}else if(1 == result) {
			log.info(notification_alert + "推送成功");
			alarmForMaterialResponse.getLinkFrame().resCode = 0;
		}
		
		//推送消息后，添加error记录，更行咖啡机的当前状态
		
		alarmForMaterialResponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(alarmForMaterialResponse);

	}

}
