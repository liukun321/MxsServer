package com.mxs.mxsserver.service;

import java.util.List;

import com.mxs.mxsserver.domain.CoffeeMachine;


public interface CoffeeMachineService {
	//根据咖啡机编号查询咖啡机
	public CoffeeMachine getCoffeeMachineInfo(String machineCode);
	
	//根据员工编号查询职责内的所有咖啡机
	public List<CoffeeMachine> machineForEmployee(String employeeCode);
	//查询所有咖啡机的信息
	public List<CoffeeMachine> getAllCoffeeMachine();
	//添加咖啡机
	public CoffeeMachine addCoffeeMachine(CoffeeMachine coffeeMachine);
	//批量查询咖啡机
	public List<CoffeeMachine> queryMachinesByCode(List<String> machineCodes);
	
	//批量保存
	public void addCoffeeMachines(List<CoffeeMachine> list);
	
}
