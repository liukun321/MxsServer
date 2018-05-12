package com.mxs.mxsserver.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 运营人员的查询
 * @author liukun
 *
 */

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.util.MD5;
import com.mxs.mxsserver.util.StringUtils;
import com.mxs.mxsserver.vo.EmployeeVo;
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CoffeeMachineService coffeeMachineService;
	/**
	 * 1 运营人员新增和删除
	 * 2运营人员信息更改
	 * 3 统计运营人员的绩效，即造成咖啡机报警和停机的总时间
	 * 4运营人员查看负责的咖啡机信息
	 */
	@PostMapping("/addEmployee")
	public ResultBean insertEmployee(@RequestBody EmployeeVo employee) {
		log.info(employee.toString());
		employee.setPassword(MD5.md5(employee.getPassword()));
		Employee empl = new Employee();
		BeanUtils.copyProperties(employee, empl);
		empl.setJoinTime(new Date());
		UUID employeeCode = UUID.randomUUID();
		empl.setEmployeeCode(employeeCode.toString());
		employeeService.insertEmployee(empl);
		return ResultBean.ok();
	}
	
	@GetMapping("/queryMachine")
	public ResultBean queryMachine4Employee(String employeeCode) {
		List<CoffeeMachine> ms = coffeeMachineService.machineForEmployee(employeeCode);
		if(null == ms) {
			return ResultBean.error();
		}
		return ResultBean.ok(ms);
	}
	@GetMapping("/updateEmployee")
	public ResultBean updateEmployee(@RequestBody EmployeeVo employee) {
		String employeeCode = employee.getEmployeeCode();
		if(!StringUtils.isNull(employeeCode)) {
			Employee empl = employeeService.queryEmployeeById(employeeCode);
			if(null != empl) {
				BeanUtils.copyProperties(employee, empl);
				employeeService.insertEmployee(empl);
				return ResultBean.ok(empl);
			}
		}
		return ResultBean.error();
	}
}
