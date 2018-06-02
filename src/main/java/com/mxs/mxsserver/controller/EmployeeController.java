package com.mxs.mxsserver.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.CoffeeMachineAddress;
import com.mxs.mxsserver.domain.CoffeeMaterial;
import com.mxs.mxsserver.domain.ConnectCenter;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.domain.ErrorRecord;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.domain.WorkerFeedback;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.CoffeeMaterialService;
import com.mxs.mxsserver.service.ConnectCenterService;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.service.ErrorRecordService;
import com.mxs.mxsserver.service.FeedbackService;
import com.mxs.mxsserver.service.MachineAddressService;
import com.mxs.mxsserver.util.DateUtils;
import com.mxs.mxsserver.util.MD5;
import com.mxs.mxsserver.util.MxsConstants;
import com.mxs.mxsserver.util.StringUtils;
import com.mxs.mxsserver.vo.result.ConnectVo;
import com.mxs.mxsserver.vo.result.DataInfo;
import com.mxs.mxsserver.vo.result.InfoData;
import com.mxs.mxsserver.vo.result.LoginVo;
import com.mxs.mxsserver.vo.result.MachineInfoList;
import com.mxs.mxsserver.vo.result.MachineInfoVo;
import com.mxs.mxsserver.vo.result.MachineListVo;
import com.mxs.mxsserver.vo.result.MachineVo;
import com.mxs.mxsserver.vo.result.MachineWaringVo;
import com.mxs.mxsserver.vo.result.MachineWarningList;
import com.mxs.mxsserver.vo.result.MainPage;
import com.mxs.mxsserver.vo.result.MainPageVo;
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
	@Autowired
	private ConnectCenterService connectCenterService;
	@Autowired
	private CoffeeMaterialService coffeeMaterialService;
	@Autowired
	private MachineAddressService machineAddressService;
	@Autowired
	private ErrorRecordService eErrorRecordService;
	
	private static Map<String, HttpSession> sessionMap = new HashMap();
	/**
	 * 1 运营人员新增和删除
	 * 2运营人员信息更改
	 * 3 统计运营人员的绩效，即造成咖啡机报警和停机的总时间
	 * 4运营人员查看负责的咖啡机信息
	 */
	
	/**
	 * 运营人员登陆
	 * @param phone 登陆的用户名，即电话，在后端分配
	 * @param password 登陆密码，初始密码是123456
	 * @return
	 */
	@PostMapping("/user/login")
	public ResultBean login(HttpServletRequest request) {//
//		, @RequestParam(required = true) String phone,@RequestParam(required = true)String password
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
		login.setSessionId(session.getId());
		String sessionId = session.getId();
		sessionMap.put(sessionId, session);
		session.setAttribute("tokens", token);
		session.setAttribute("phone", phone);
		log.info(login.toString() + "---" + session.getAttribute("tokens") + "--" + session.getAttribute("phone"));
		return ResultBean.ok(login);
	}
	@PostMapping("/work/map")
	public ResultBean userMainpage(HttpServletRequest request) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MainPageVo> result = new ArrayList();//返回的结果集
		MainPage resultList = new MainPage();
		String workerId = request.getParameter("workerId");
		try {
			//查询运营人员负责的咖啡机
			List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
			log.info("咖啡机数量" + list.size());
			for (CoffeeMachine coffeeMachine : list) {
				MainPageVo main = new MainPageVo();
				main.setMachineId(coffeeMachine.getMachineId());
				result.add(main);
				main.setIs_running(coffeeMachine.getIs_running());
				//查询当前咖啡机所有缺料的料盒
				List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByStatus(coffeeMachine.getMachineId(), 1);
				log.info("料盒数量" + ms.size() + "---" + ms.toString());
				if (ms.isEmpty()) {
					main.setDanger(0);
				} else {
					main.setDanger(1);
					List<DataInfo> map = new ArrayList();
					//将所有的缺料料盒信息放进map中
					for (CoffeeMaterial cm : ms) {
						DataInfo di = new DataInfo();
						di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
						map.add(di);
					}
					main.setTypeList(map);
				}
				//获取咖啡机的地址位置
				CoffeeMachineAddress addr = machineAddressService.queryMachineAddressById(coffeeMachine.getMachineId());
				main.setLatitude(addr.getLatitude());
				main.setLongitude(addr.getLongitude());
				//确定咖啡机报警的时间
				List<ErrorRecord> errs = eErrorRecordService.queryErrorRecord(coffeeMachine.getMachineId());
				String time = sdfmat.format(errs.get(0).getStartTime());
				main.setTime(time);
			} 
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
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
			return ResultBean.error(MxsConstants.CODE1, "新旧密码为空");
		}
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		Employee em = employeeService.queryEmployeeByPhone(phone);
		if(null == em) 
			return ResultBean.error(MxsConstants.CODE, phone + "登陆失效，请重新登陆");
		String pwd = em.getPassword();
		if(pwd.equals(MD5.md5(oldPwd))) {
			em.setPassword(MD5.md5(newPwd));
			employeeService.insertEmployee(em);
		}else {
			return ResultBean.error(MxsConstants.CODE0, phone + "原始密码不正确，请重新输入");
		}
		return ResultBean.ok();
	}
	
	/**
	 * 查询用户信息
	 * @return
	 */
	@PostMapping("/work/machine/infos")
	public ResultBean userInfo(HttpServletRequest request) {
		MachineInfoList resultList = new MachineInfoList();
		List<MachineInfoVo> result = new ArrayList();
		String machineId = request.getParameter("machineId");
		String workerId = request.getParameter("workerId");
		try {
			if(StringUtils.isNull(machineId)) {
				List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
				log.info("咖啡机数量" + list.size());
				for (CoffeeMachine coffeeMachine : list) {
					MachineDetail(result, coffeeMachine);
				} 
			}else {
				CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineId);
				MachineDetail(result, cm);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	/**
	 * 获取指定咖啡机的详情
	 * @param result
	 * @param coffeeMachine
	 */
	private void MachineDetail(List<MachineInfoVo> result, CoffeeMachine coffeeMachine) {
		MachineInfoVo main = new MachineInfoVo();
		main.setMachineId(coffeeMachine.getMachineId());
		result.add(main);
		main.setMachineVersion(coffeeMachine.getType());
		//查询当前咖啡机所有料盒
		List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByMachineId(coffeeMachine.getMachineId());
		log.info("料盒数量" + ms.size() + "---" + ms.toString());
		List<InfoData> map = new ArrayList();
		//将所有的缺料料盒信息放进map中
		for (CoffeeMaterial cm : ms) {
			InfoData di = new InfoData();
			di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
			di.setDanger(cm.getStatus());
			map.add(di);
		}
		main.setList(map);
	}
	
	@PostMapping("/work/machine/warning")
	public ResultBean userMachineWaning(HttpServletRequest request) {
		MachineWarningList resultList = new MachineWarningList();
		List<MachineWaringVo> result = new ArrayList();
		String machineId = request.getParameter("machineId");
		String workerId = request.getParameter("workerId");
		if(StringUtils.isNull(machineId)) {
			List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
			log.info("咖啡机数量" + list.size());
			for (CoffeeMachine coffeeMachine : list) {
				MachineWarningDetail(result, coffeeMachine);
			} 
		}else {
			CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineId);
			MachineWarningDetail(result, cm);
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	
	/**
	 * 获取指定咖啡机的详情
	 * @param result
	 * @param coffeeMachine
	 */
	private void MachineWarningDetail(List<MachineWaringVo> result, CoffeeMachine coffeeMachine) {
		MachineWaringVo main = new MachineWaringVo();
		main.setMachineId(coffeeMachine.getMachineId());
		result.add(main);
		//查询当前咖啡机所有料盒
		List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByStatus(coffeeMachine.getMachineId(), 1);
		log.info("料盒数量" + ms.size() + "---" + ms.toString());
		List<DataInfo> map = new ArrayList();
		//将所有的缺料料盒信息放进map中
		for (CoffeeMaterial cm : ms) {
			DataInfo di = new DataInfo();
			di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
			map.add(di);
		}
		main.setTypeList(map);
	}
	
	
	/**
	 * 获取运营人员负责的咖啡机列表
	 * @param keyword
	 * @return
	 */
	@PostMapping("/work/machine/list")
	public ResultBean userMachineList(HttpServletRequest request) {//keyword  具体含义      @RequestParam(required = false)String keyword
		Map<String, Integer> machines = null;
		MachineListVo resultList = new MachineListVo();
//		String keyword = request.getParameter("keyword");
		List<MachineVo> result = new ArrayList();
		HttpSession session = request.getSession();
		String workerId = request.getParameter("workerId");
		Employee em = employeeService.queryEmployeeById(workerId);
		machines = em.getMachines();
		//将map转换为APP端需要的数据格式 
		for (Entry<String, Integer> entry : machines.entrySet()) {
			MachineVo  mv = new MachineVo();
			result.add(mv);
			mv.setMachineId(entry.getKey());
			mv.setMachineVersion(entry.getValue());
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	/**
	 * 运营人员反馈
	 * @param request
	 * @return
	 */
	@PostMapping("/work/feedback")
	public ResultBean userFeedback(HttpServletRequest request) {//keyword  具体含义
		HttpSession session = request.getSession();
		String message = request.getParameter("message");
		Integer type = Integer.parseInt(request.getParameter("type"));
		if(StringUtils.isNull(message) || StringUtils.isNull(type))
			return ResultBean.error(MxsConstants.CODE1, "类型或者反馈信息为空");
		String phone = (String) session.getAttribute("phone");
		WorkerFeedback back = new WorkerFeedback();
		back.setCreateTime(new Date());
		back.setWorkerId(phone);
		back.setDescription(message);
		back.setType(type);
		back = feedbackService.addFeedback(back);
		if(null == back)
			return ResultBean.error(MxsConstants.CODE1, "反馈保存失败");
		return ResultBean.ok();
	}
	
	@PostMapping("/work/call")
	public ResultBean userCallList(HttpServletRequest request) {
		ConnectVo cvo = new ConnectVo();
		List<ConnectCenter> list = connectCenterService.queryAllConnection();
		if(null == list || list.isEmpty())
			return ResultBean.error();
		cvo.setList(list);
		return ResultBean.ok(cvo);
	}
}
