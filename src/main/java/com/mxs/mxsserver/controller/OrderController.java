package com.mxs.mxsserver.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.domain.ResultBean;
import com.mxs.mxsserver.service.PayindentService;
/**
 * 订单查询
 * @author liukun
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	private final Logger log = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private PayindentService payindentService;
	/**
	 * 查询订单的总金额
	 * @param status 0 待支付；1 支付成功；2 支付失败； 3:退款成功 ；4：退款失败 5 订单作废
	 * 
	 * @param payMethod 支付方式 1：支付宝； 2 微信
	 * @return
	 */
	@GetMapping("/sumprice")
	public ResultBean getSumprice4Order(Integer status, Integer payMethod) {
		Double sum = payindentService.querySumprice(status, payMethod);
		log.info("Sum price:" + sum);
		//对不同状态的订单的金额进行 统计，供财务报表使用
//		Double sumPrice = 0.0;
//		for (Payindent payindent : orders) {
//			sumPrice += payindent.getPrice();
//		}
		return ResultBean.ok(sum);
		
	}
	/**
	 * 根据状态查询订单详情
	 * @param status
	 * @return
	 */
	@GetMapping("/queryOrderInfo")
	public ResultBean queryOrderInfo(Integer status) {
		List<Payindent> orders= payindentService.queryOrderByStatus(status);
		if(null == orders)
			return ResultBean.error();
		return ResultBean.ok(orders);
		
	}
	/**
	 * 根据订单ID查询
	 * @param indentId
	 * @return
	 */
	@GetMapping("/queryOrderById")
	public ResultBean queryOrderInfoById(String indentId) {
		Payindent order= payindentService.queryPayindentById(indentId);
		if(null == order)
			return ResultBean.error();
		return ResultBean.ok(order);
		
	}
}
