package com.mxs.mxsserver.handler.coffee;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PayBarCodeRequest;
import com.mxs.mxsserver.protocol.responce.coffee.PayBarCodeResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.CouponsService;
import com.mxs.mxsserver.service.PayindentService;
import com.mxs.mxsserver.util.AlipayUtils;
import com.mxs.mxsserver.util.DateUtils;
import com.mxs.mxsserver.util.StringUtils;
import com.mxs.mxsserver.util.WechatPayUtils;
/**
 * 3 单杯购买订单
 * @author liukun
 *	在咖啡机上选定好咖啡的品种后，点击下单并扫描条形码后向后端发情支付的请求
 *  生成订单，同时调用 第三方接口进行支付
 *  调用成功后，给APP端反馈，之后在请求订单状态查询
 *  
 *  订单取消失败怎么处理
 */
@Component
public class PayBarCodeRequestHandler extends RequestHandler {
	//日志记录
	private final Logger log = LoggerFactory.getLogger(PayBarCodeRequestHandler.class);
	/**
	 * 引入service实例
	 */
	@Autowired
	private CoffeeInfoService service;
	@Autowired
	private CouponsService cservice;
	@Autowired
	private PayindentService pservice;
	private static CoffeeInfoService coffeeInfoService;
	private static PayindentService payindentService;
	private static CouponsService couponsService;
	@PostConstruct
	public void init() {
		coffeeInfoService = service;
		payindentService = pservice;
		couponsService = cservice;
	}
		
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		boolean flag = true;//用于标识付款过程中的异常情况，终止支付请求
		PayBarCodeRequest payBarCodeRequest = (PayBarCodeRequest) request;
		
		boolean tradeStatus = false;
		
		log.info("----开始支付----");
		//TODO 参数验证
		String coffeeid = payBarCodeRequest.getCoffeeId();
		String ip = payBarCodeRequest.getIp();
		String discount = payBarCodeRequest.getDiscount();
		if(StringUtils.isNull(discount)) {
			discount = "0.0";
		}
		String auth_code = payBarCodeRequest.getAuthCode();
//		String auth_code = "135179978859858706";
		String venderName = payBarCodeRequest.getMachineId();
		int payMethod = 0;
		Double price = 0.0, reduce_price = 0.0;
		boolean isHot = payBarCodeRequest.isHot();
		int sugar = payBarCodeRequest.getSugar();
		
		//根据auth_code判断支付方式
		if(!StringUtils.isNull(auth_code)) {
			String pre = auth_code.substring(0, 2);
			int authPre = Integer.parseInt(pre);
			if(authPre > 9 && authPre < 15)//微信支付
				payMethod = 2;
			if(authPre > 24 && authPre < 31)//支付宝支付
				payMethod = 1;
		}
		//确定 咖啡的原价和折扣价
		if(!StringUtils.isNull(coffeeid)) {
			//咖啡原价
			try {
				price = coffeeInfoService.queryCoffeeInfoForPrice(Integer.parseInt(coffeeid));
				//优惠后的价钱
				if (!StringUtils.isNull(price)) {
					reduce_price = price - Double.valueOf(discount);
					if (reduce_price < 0) {//当优惠后的价格小于零后，设置为0
						reduce_price = 0.1;
					}
				}else {
					//TODO 购买取消
					flag = false;
				}
			} catch (Exception e) {
				log.info(e.getMessage());
				flag = false;
			}
		}
		//生成订单的ID  咖啡机Id + 咖啡Id + 时间戳
		String indent_id = venderName + coffeeid + DateUtils.dateFormat("yyyyMMddHHmmss");
		log.info("订单Id = " + indent_id);
		//将订单入库
		Payindent payindent = null;
		if(flag) {
			payindent = new Payindent();
			payindent.setIndentId(indent_id);
			payindent.setMachineId(venderName);
			payindent.setCoffeeId(coffeeid);
			payindent.setPrice(reduce_price);
			payindent.setPriceOri(price);
			payindent.setPayMethod(payMethod);
			payindent.setPayStatus(0);
			payindent.setCreateTime(new Date());
			payindent.setHot(isHot);
			payindent.setSugar(sugar);
			payindent = payindentService.addPayindent(payindent);
		}
		//设置返回的值
		PayBarCodeResponce payBarCodeResponce = new PayBarCodeResponce(request.getLinkFrame().key);
		payBarCodeResponce.setCoffeeId(coffeeid);
		payBarCodeResponce.setPayIndent(indent_id);
		payBarCodeResponce.setPrice(Double.toString(reduce_price));
		
 		if (1 == payMethod && flag) {
			//支付宝支付
			String tradeNo = "";
			try {
				AlipayTradePayResponse alipayTradePayResponse = AlipayUtils.alipayByBarCode(indent_id, auth_code, reduce_price);
				tradeNo = alipayTradePayResponse.getTradeNo();//支付宝返回的交易号
				payindent.setOrderId(tradeNo);
				//支付成功
				log.info("支付宝返回的交易号:" + tradeNo);
				if ("10000".equals(alipayTradePayResponse.getCode())) {
					//更新支付状态
					tradeStatus = true;
				}
			} catch (AlipayApiException e) {
				log.error("支付异常，取消订单：" + e.getErrMsg());
				try {
					AlipayTradeCancelResponse alipayTradeCancelResponse = AlipayUtils.alipayTradeCancel(indent_id, tradeNo);
					if (alipayTradeCancelResponse.isSuccess()) {
						log.info("AlipayTradeCancel--调用成功");
					} else {
						log.info("AlipayTradeCancel--调用失败");
					}
				} catch (AlipayApiException e1) {
					log.error("订单取消异常：" + e1.getErrMsg());
				}
			}
			
		}else if (2 == payMethod && flag) {
			// TODO 微信支付 微信支付中返回结果中又return_code：表示请求成功 和 result_code：表示请求执行成功
			//总价的单位时元，传入微信接口时需要转换为分  body字段的定义
			String value = StringUtils.convertDoubleToStr(reduce_price);
			try {
				Map<String, String> resultMap = WechatPayUtils.payByWechat(indent_id, auth_code,
						value, ip);//Double.toString(reduce_price*100).replaceAll(".", "")
				String result_code = resultMap.get("result_code");
				String return_code = resultMap.get("return_code");
				String err_code = resultMap.get("err_code");
				String transaction_id = resultMap.get("resultMap");//获取微信支付订单号
				payindent.setOrderId(transaction_id);
				log.info("return_code=" + return_code + "--err_code = " + err_code + "--result_code= " + result_code);
				//
				if ("SUCCESS".equals(return_code)){//请求成功
					//返回给APP，订单是待支付状态
//					ctx.getChannel().write(payBarCodeResponce);//反馈给APP
					log.info("----------------微信支付请求成功----------------" + resultMap.toString());
					if ("SUCCESS".equals(result_code)) {//支付成功
						log.info("----------------微信支付扣款成功----------------");
						tradeStatus = true;
					}
					if ("USERPAYING".equals(err_code)){
						//用户正在支付中，等待10秒钟后再查询订单详情
						String query_result = "";
						for(int i=0; i < 3; i++) {
							//最多循环三次，30秒超时
							Thread.sleep(10000);
							Map<String, String> queryMap = WechatPayUtils.queryOrder(indent_id);
							String queryCode = queryMap.get("result_code");
							query_result = queryMap.get("return_code");
							if("SUCCESS".equals(queryCode) && "SUCCESS".equals(query_result)) {
								tradeStatus = true;
								break;
							}
						}
						//三次结束之后还是失败
						if(!"SUCCESS".equals(query_result)) {
							log.info("----------------微信支付扣款成功失败----------------");
						}
						//超时后系统会自动取消订单？？？
					}
					if("SYSTEMERROR".equals(err_code)) {//NOTENOUGH：余额不足
						//系统错误则，查询订单，返回订单的实际结果
						Thread.sleep(5000);
						Map<String, String> queryMap = WechatPayUtils.queryOrder(indent_id);
						String queryCode = queryMap.get("result_code");
						String query_result = queryMap.get("return_code");
						if("SUCCESS".equals(queryCode) && "SUCCESS".equals(query_result)) {
							tradeStatus = true;
						}
					}
				}
			} catch (Exception e) {
				log.error("微信支付过程异常：" + e.getMessage());
			}
		}else {
			log.info("支付方式枚举值传递有误：{}", payMethod);
		}
		//TODO 支付失败，订单详情需要入库？？
		
//		tradeStatus = true;
		log.info("----22222----" + tradeStatus);
		if(tradeStatus) {
			paySuccess(payindent, payBarCodeResponce);
		}else {
			payFailure(payindent, payBarCodeResponce);
		}
		ctx.getChannel().write(payBarCodeResponce);//反馈给APP
		payindentService.addPayindent(payindent);//更新订单状态
	}
	/**
	 * 支付失败
	 * @param pay
	 * @param payBarCodeResponce
	 */
	private void payFailure(Payindent pay, PayBarCodeResponce payBarCodeResponce) {
		pay.setPayStatus(2);
		payBarCodeResponce.setStatus("2");
		payBarCodeResponce.getLinkFrame().resCode = 500;
	}
	/**
	 * 支付成功
	 * @param pay
	 * @param payBarCodeResponce
	 */
	private void paySuccess(Payindent pay, PayBarCodeResponce payBarCodeResponce) {
		
		pay.setPayStatus(1);
		payBarCodeResponce.setStatus("1");
		payBarCodeResponce.getLinkFrame().resCode = 200;
	}
}
