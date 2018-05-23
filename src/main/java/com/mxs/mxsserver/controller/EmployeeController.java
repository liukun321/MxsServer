package com.mxs.mxsserver.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.domain.WorkerFeedback;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.service.FeedbackService;
import com.mxs.mxsserver.util.DateUtils;
import com.mxs.mxsserver.util.MD5;
import com.mxs.mxsserver.util.MxsConstants;
import com.mxs.mxsserver.util.StringUtils;
import com.mxs.mxsserver.vo.EmployeeVo;
import com.mxs.mxsserver.vo.result.LoginVo;
import com.mxs.mxsserver.vo.result.MachineVo;
@RestController
@RequestMapping("/coffee/worker")
public class EmployeeController {
	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CoffeeMachineService coffeeMachineService;
	@Autowired
	private FeedbackService feedbackService;
//	@Autowired
//	private HttpServletRequest request;
	private static Map<String, HttpSession> sessionMap = new HashMap();
	/**
	 * 1 运营人员新增和删除
	 * 2运营人员信息更改
	 * 3 统计运营人员的绩效，即造成咖啡机报警和停机的总时间
	 * 4运营人员查看负责的咖啡机信息
	 */
	@PostMapping("/addEmployee")
	public ResultBean insertEmployee(EmployeeVo employee) {
		log.info(employee.toString());
		employee.setPassword(MD5.md5(employee.getPassword()));
		Employee empl = new Employee();
		BeanUtils.copyProperties(employee, empl);
		UUID employeeCode = UUID.randomUUID();
		empl.setWorkerId(employeeCode.toString());
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
	
	/**
	 * 运营人员登陆
	 * @param phone 登陆的用户名，即电话，在后端分配
	 * @param password 登陆密码，初始密码是123456
	 * @return
	 */
	@PostMapping("/user/login")
	public ResultBean login(HttpServletRequest request) {//@RequestParam(required = true) String phone,@RequestParam(required = true)String password
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		log.info("----------登陆请求成功-----------");
		if(StringUtils.isNull(phone) || StringUtils.isNull(password)) {
			return ResultBean.error(MxsConstants.CODE0, "电话或者密码为空");
		}
		String md5pass = MD5.md5(password);
		Employee em = employeeService.login4Employee(phone, md5pass);
		if(null == em)
			return ResultBean.error(MxsConstants.CODE0, "登陆失败，请确认用户名和密码是否正确");
		LoginVo login = new LoginVo();
		BeanUtils.copyProperties(em, login);
		String token = MD5.md5(phone + DateUtils.dateFormat("yyyyMMddHHmmss"));
		login.setToken(token);
		HttpSession session = request.getSession(true);
		log.info("当前会话的Id = " + session.getId());
		String sessionId = session.getId();
		sessionMap.put(sessionId, session);
		session.setAttribute("tokens", token);
		session.setAttribute("phone", phone);
		log.info(login.toString() + "---" + session.getAttribute("tokens") + "--" + session.getAttribute("phone"));
		return ResultBean.ok(login);
	}
	
	/**
	 * 用户更新登陆密码
	 * @param oldPwd 原始乎密码
	 * @param newPwd 新密码
	 * @return
	 */
	@PostMapping("/user/pwd/change")
	public ResultBean userUpdatePassword(HttpServletRequest request) {
//		@RequestParam(required = true) String oldPwd,
//		@RequestParam(required = true)String newPwd
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		log.info("---------修改密码请求成功-----------");
		if(StringUtils.isNull(oldPwd) || StringUtils.isNull(newPwd)) {
			return ResultBean.error(MxsConstants.CODE0, "新旧密码为空");
		}
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		Employee em = employeeService.queryEmployeeByPhone(phone);
		if(null == em) 
			return ResultBean.error(MxsConstants.CODE1, phone + "登陆失效，请重新登陆");
		String pwd = em.getPassword();
		if(pwd.equals(MD5.md5(oldPwd))) {
			em.setPassword(MD5.md5(newPwd));
			employeeService.insertEmployee(em);
		}else {
			return ResultBean.error(MxsConstants.CODE1, phone + "原始密码不正确，请重新输入");
		}
		return ResultBean.ok();
	}
	
	
	@PostMapping("/user/info")
	public ResultBean userInfo() {
		//TODO UserInfo 表字段未定
		
		return ResultBean.ok();
	}
	/**
	 * 获取运营人员负责的咖啡机列表
	 * @param keyword
	 * @return
	 */
	@PostMapping("/work/material/list")
	public ResultBean userMachineList(HttpServletRequest request) {//keyword  具体含义      @RequestParam(required = false)String keyword
		Map<String, Integer> machines = null;
		String keyword = request.getParameter("keyword");
		List<MachineVo> result = new ArrayList();
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		if(StringUtils.isNull(keyword)) {
			Employee em = employeeService.queryEmployeeByPhone(phone);
			machines = em.getMachines();
		}
		//将map转换为APP端需要的数据格式
		for (Entry<String, Integer> entry : machines.entrySet()) {
			MachineVo  mv = new MachineVo();
			result.add(mv);
			mv.setMaterialId(entry.getKey());
			mv.setMaterialVersiion(entry.getValue());
		}
		return ResultBean.ok(result);
	}
	
	@PostMapping("/work/feedback")
	public ResultBean userFeedback(HttpServletRequest request) {//keyword  具体含义
		HttpSession session = request.getSession();
		String message = request.getParameter("message");
		String phone = (String) session.getAttribute("phone");
		WorkerFeedback back = new WorkerFeedback();
		back.setCreateTime(new Date());
		back.setWorkerId(phone);
		back.setDescription(message);
		back = feedbackService.addFeedback(back);
		if(null == back)
			return ResultBean.error(MxsConstants.CODE1, "反馈保存失败");
		return ResultBean.ok(back);
	}
}
