package com.mxs.mxsserver.controller;

import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.util.GenerateUniqueId;
import com.mxs.mxsserver.util.MxsConstants;
import com.mxs.mxsserver.vo.CoffeeInfoVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coffeeInfo")
public class CoffeeInfoController {
	private final Logger log = LoggerFactory.getLogger(CoffeeInfoController.class);
	@Autowired
	private CoffeeInfoService coffeeInfoService;
	/**
	 * 根据咖啡机编号查询咖啡机
	 * @param coffeeInfoVo
	 * @return
	 */
	
	@PostMapping("/addCoffee")
	public ResultBean addCoffeeInfo(CoffeeInfoVo coffeeInfoVo) {
		CoffeeInfo coffeeInfo = new CoffeeInfo();
		BeanUtils.copyProperties(coffeeInfoVo, coffeeInfo);
		Integer coffeeId = GenerateUniqueId.generateCoffeeId();
		coffeeInfo.setCoffeeId(coffeeId.toString());
		CoffeeInfo c = coffeeInfoService.addCoffeeInfo(coffeeInfo);
		if(null ==  c) {
			return ResultBean.error(MxsConstants.CODE1, "Add coffeeInfo failure");
		}
		return ResultBean.ok();
	}
}
