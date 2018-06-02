package com.mxs.mxsserver.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.util.MxsConstants;

/**
 *咖啡机查询 
 * @author liukun
 *
 */
@RestController
@RequestMapping("/machine")
public class CoffeeMachineController {
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineController.class);
	@Autowired
	private CoffeeMachineService coffeeMachineService;
	/**
	 * 根据咖啡机编号查询咖啡机
	 * @param machineCode
	 * @return
	 */
	
	@GetMapping("/getOneMachineInfo")
	public ResultBean getCoffeeMachineInfo(String machineCode) {
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineCode);
		if(null == cm) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		return ResultBean.ok(cm);
	}
	/**
	 * 查询运维人员负责的所有咖啡机
	 * @param employeeCode
	 * @return
	 */
	@GetMapping("/coffeeMachineForEmployee")
	public ResultBean CoffeeMachineForEmployee(String employeeCode) {
		List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(employeeCode);
		if(null == list || list.isEmpty()) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		return ResultBean.ok(list);
	}
	/**
	 * 查询所有咖啡机信息
	 * @return
	 */
	@GetMapping("/allMachine")
	public ResultBean getAllCoffeeMachine() {
		List<CoffeeMachine> list = coffeeMachineService.getAllCoffeeMachine();
		return ResultBean.ok(list);
	}
	
	/*@PostMapping("/addCoffeeMachine")
	public ResultBean addCoffeeMachine(@RequestBody CoffeeMachineVo coffeeMachineVo) {
		CoffeeMachine coffeeMachine = new CoffeeMachine();
		try {
			BeanUtils.copyProperties(coffeeMachineVo, coffeeMachine);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			coffeeMachine.setTimestamps(Long.parseLong(timestamp.toString()));
			//生成咖啡机编号
			String machineId = GenerateUniqueId.generateMachineId();
			coffeeMachine.setVenderName(machineId);
			coffeeMachineService.addCoffeeMachine(coffeeMachine);
			
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return ResultBean.error(MxsConstants.CODE1, e.getMessage());
		}
		return ResultBean.ok();
	}*/
	/**
	 * 在新增运维人员的同时跟新绑定的咖啡机信息
	 * @return
	 */
	@PutMapping("/updateCoffeeMachine")
	public ResultBean updateCoffeeMachine(@RequestBody List<String> machineCodes, String employeeCode) {
		List<CoffeeMachine> list = coffeeMachineService.queryMachinesByCode(machineCodes);
		if(null == list || list.isEmpty()) {
			return ResultBean.error(MxsConstants.CODE1, "咖啡机不存在--" + machineCodes);
		}
		list.stream().forEach(machine -> machine.setEmployeeCode(employeeCode));
		coffeeMachineService.addCoffeeMachines(list);
		return ResultBean.ok();
	}
	
	/**
	 *1 咖啡机异常出现和结束调用的接口
	 *2 咖啡机物料出现报警、短缺等情况需要调用的接口
	 *3 咖啡机的运营人员信息变更
	 */
}
