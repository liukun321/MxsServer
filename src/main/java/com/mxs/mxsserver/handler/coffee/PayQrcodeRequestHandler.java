package com.mxs.mxsserver.handler.coffee;

import java.sql.Timestamp;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PayQrcodeRequest;
import com.mxs.mxsserver.protocol.responce.coffee.PayNotifyResponce;
import com.mxs.mxsserver.protocol.responce.coffee.PayQrcodeResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.PayindentService;
import com.mxs.mxsserver.util.AlipayUtils;
import com.mxs.mxsserver.util.DateUtils;
//单杯购买订单
@Component
public class PayQrcodeRequestHandler extends RequestHandler {
	//日志记录
	private final Logger log = LoggerFactory.getLogger(PayQrcodeRequestHandler.class);
	/**
	 * 引入service实例
	 */
	@Autowired
	private CoffeeInfoService service;
	@Autowired
	private PayindentService pservice;
	private static CoffeeInfoService coffeeInfoService;
	private static PayindentService payindentService;
	@PostConstruct
	public void init() {
		coffeeInfoService = service;
		payindentService = pservice;
	}
		
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
//		PayQrcodeRequest payQrcodeRequest = new PayQrcodeRequest();
		
		/*AlipayTradePayResponse alipayTradePayResponse = AlipayUtils.alipayByBarCode(indent_id, "auth_code", totalprice);
		Payindent pi = payindentService.queryPayindentById(indent_id);
		Optional<Payindent> pay = Optional.ofNullable(pi);
		//支付成功
		if (alipayTradePayResponse.getCode() == "10000" && pay.isPresent()) {
//			int flag2 = database.updateindentstatu(indent_id);
			//更新支付状态
			pay.get().setPayStatus(1);
//			payindentService.addPayindent(pi);
			PayNotifyResponce payNotifyResponce = new PayNotifyResponce(request.getLinkFrame().key);
			payNotifyResponce.setCoffeeindent(payQrcodeCartRequest.getCoffeeIndents());
			payNotifyResponce.getLinkFrame().resCode = 200;
			ctx.getChannel().write(payNotifyResponce);
		}else {
			pay.get().setPayStatus(2);
		}
		payindentService.addPayindent(pi);*/
		
		
		
		
		PayQrcodeRequest payQrcodeRequest = (PayQrcodeRequest)request;
		System.out.println(payQrcodeRequest.getCoffeeId());
		coffeeInfoService.queryCoffeeInfoForPrice(payQrcodeRequest.getCoffeeId());
		System.out.println(payQrcodeRequest.getDosing());
		System.out.println(payQrcodeRequest.getProvider());
		PayQrcodeResponce payQrcodeResponce = new PayQrcodeResponce(payQrcodeRequest.getLinkFrame().key);
		payQrcodeResponce.getLinkFrame().serialId = payQrcodeResponce.getLinkFrame().serialId;
		payQrcodeResponce.setPrice("15");
		payQrcodeResponce.setQrcodeUrl("10.10.10.10");
		payQrcodeResponce.setCoffeeIndent("coffeeintent");
		ctx.getChannel().write(payQrcodeResponce);
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
		AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
		
//		Timestamp time = new Timestamp(System.currentTimeMillis());
		String indent_id = request.getLinkFrame().key + DateUtils.dateFormat("yyyyMMddHHmmss");
		
		//条形码支付
		alipayTradePayRequest.setBizContent("{" +
		//商户订单号
		"\"out_trade_no\":" + "\"" + indent_id + "\"" + "," +
		//支付场景 条码支付，取值：bar_code ；声波支付，取值：wave_code
		"\"scene\":\"bar_code\"," +
		//支付授权码
		"\"auth_code\":\"28763443825664394\"," +
		//销售产品码 
		"\"product_code\":\"FACE_TO_FACE_PAYMENT\"," +
		//订单标题 
		"\"subject\":\"Iphone6 16G\"," +
		//买家的支付宝用户id 如果为空，会从传入了码值信息中获取买家ID 
		"\"buyer_id\":\"2088202954065786\"," +
//		"\"seller_id\":\"2088102146225135\"," +
		//
		"\"total_amount\":88.88," +
		"\"trans_currency\":\"USD\"," +
		"\"settle_currency\":\"USD\"," +
		"\"discountable_amount\":8.88," +
		"\"body\":\"Iphone6 16G\"," +
		"      \"goods_detail\":[{" +
		"        \"goods_id\":\"apple-01\"," +
				"\"goods_name\":\"ipad\"," +
				"\"quantity\":1," +
				"\"price\":2000," +
				"\"goods_category\":\"34543238\"," +
				"\"body\":\"特价手机\"," +
				"\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
		"        }]," +
		"\"operator_id\":\"yx_001\"," +
		"\"store_id\":\"NJ_001\"," +
		"\"terminal_id\":\"NJ_T_001\"," +
		"\"extend_params\":{" +
		"\"sys_service_provider_id\":\"2088511833207846\"," +
		"\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
		"\"card_type\":\"S0JP0000\"" +
		"    }," +
		"\"timeout_express\":\"5m\"," +
		"\"auth_confirm_mode\":\"COMPLETE：转交易支付完成结束预授权;NOT_COMPLETE：转交易支付完成不结束预授权\"," +
		"\"terminal_params\":\"{\\\"key\\\":\\\"value\\\"}\"" +
		"  }");
		try {
			AlipayTradePayResponse response = alipayClient.execute(alipayTradePayRequest);
			if (response.isSuccess()) {
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
