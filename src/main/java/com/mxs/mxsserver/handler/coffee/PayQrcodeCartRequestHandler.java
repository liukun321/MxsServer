package com.mxs.mxsserver.handler.coffee;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PayQrcodeCartRequest;
import com.mxs.mxsserver.protocol.responce.coffee.PayNotifyResponce;
import com.mxs.mxsserver.protocol.responce.coffee.PayQrcodeCartResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.PayindentService;
import com.mxs.mxsserver.util.AlipayUtils;
import com.mxs.mxsserver.util.DateUtils;

//获取支付二维码
@Component
public class PayQrcodeCartRequestHandler extends RequestHandler {
	//日志记录
	private final Logger log = LoggerFactory.getLogger(PayQrcodeCartRequestHandler.class);
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
		PayQrcodeCartRequest payQrcodeCartRequest = (PayQrcodeCartRequest) request;
		System.out.println(payQrcodeCartRequest.getCoffeeIndents());
		System.out.println(payQrcodeCartRequest.getProvider());

		JSONArray array = JSON.parseArray(payQrcodeCartRequest.getCoffeeIndents());
		JSONObject indent = new JSONObject();
		int coffeeid;
		String dosing;
		double totalprice = 0;
		for (int i = 0; i < array.size(); i++) {
			indent = array.getJSONObject(i);
			coffeeid = indent.getInteger("goodsid");
			//获取咖啡的制作流程没有用途
			dosing = indent.getString("dosing");
//			totalprice += database.Queryprice(coffeeid);
			//统计购物车中的所有商品的总价
			totalprice += coffeeInfoService.queryCoffeeInfoForPrice(coffeeid);
		}

		Timestamp time = new Timestamp(System.currentTimeMillis());
		//生成订单的ID  咖啡机Id+时间戳
		String indent_id = request.getLinkFrame().key + DateUtils.dateFormat("yyyyMMddHHmmss");
//		int flag = database.insertindent(indent_id, payQrcodeCartRequest.getCoffeeIndents(), totalprice, totalprice,
//				payQrcodeCartRequest.getProvider(), 0);
		//将订单入库
		Payindent payindent = new Payindent();
		payindent.setIndentId(indent_id);
		payindent.setCoffeeindent(payQrcodeCartRequest.getCoffeeIndents());
		payindent.setPrice(totalprice);
		payindent.setPriceOri(totalprice);
//		payindent.setPayMethod((int)payQrcodeCartRequest.getProvider());
		payindent.setPayStatus(0);
		payindent.setCreateTime(new Date());
		Payindent p = payindentService.addPayindent(payindent);
		
		//将订单详情存储到反馈详情中
		PayQrcodeCartResponce payQrcodeCartResponce = new PayQrcodeCartResponce(request.getLinkFrame().key);
		payQrcodeCartResponce.setCoffeeIndents(payQrcodeCartRequest.getCoffeeIndents());
		payQrcodeCartResponce.setPayIndent(indent_id);
		payQrcodeCartResponce.setPrice(Double.toString(totalprice));
		payQrcodeCartResponce.setPriceOri(Double.toString(totalprice));

		//支付宝支付的预处理
		String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqUnBQDVeBdV47W2BGzpy0kiMXb1ahYEXWSLUs+r/XmCLc9mEXVUfA8/3Ob/NfdopW/BDCqvNiSnIWx2wj9+7gqXnzAtn3C77govHv5vHU5EU1pUB67VVJk2mMx2SngVmezryNg8OOa4E1mVLT+AfJkO2q93mL5YU2RtldTq5iblvOb+yq+sGLSh0Qbi2a+nD2U+NFpeROAcH2p5mMY/rYWQPyyxIWX+xEF2Ap8gzvvxCYUP3QCs0sIiUyNfcNlSk7w+n2ViO3ZgHkXfOZnSNvV/L2fLHOzjbFasl7IChl+DA5Q2oYi237iOhVi2siKPvjsblyzjzaYar8D98kFRniQIDAQAB";
		String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXP/1UO5/CcmmrSu4SDeOilNVTypiHrdF+QGX2Vkv4gvnvHwXu1utoXXOp/YVVW8XgCHz3oO1b9WDVCMA9kiU2uum4chOkPOAUOS3apvZkv+pUWarNpBGArBwWNSNkRrRnBQFabnylvgWh8BQGftx6X3hl3ejUnmJ19e3TAyOhHMc0i2CSXz34m2RwHoik9nJ20gAhlCzToQNqVRSbHOD2lZlYDGP7e2NoVDQ6HJFxM3u09qGLm34ZijX9j5/NHEwWZSQi3iz7gT2iqc8fRXFIMiLpPrn37nCJm/eIWOo6nxnJzoDWjkghF6xejiMnigGxo5Ari36lB3XfOS8V6reFAgMBAAECggEAEt+nSwWNsAP676cQAiy9hSIxZJ1ZX0TvaWO71Xt7S218prwmT2F+Sq2uqz25j6c+D/C/N19bfyglhy/qXUSJZET6uUty7AG0rJFeCXTgNj5EjXYK/FJG5NmVP6gt3Gt+Q4S6YIB9CWmm5khBF3ZpKNQVABEq5q1E+BdSMAyqO4ZcfjoC6hb7sIlRvhO9JOYfInGxOL55sZRhi87oG7xoSoE7xsZ2mKigG3kkjMV/4ziVfxA4cgQmVZ9EkMneTTvQi4kqCXrPDaAdrsgl16l6WhNWHdJ5EPSo04hv9u+oBAEfsddCJvYff+1eEeYzu/peV2ua9eP/1aJVDwwOqFWFgQKBgQDXVB0Vdd0YxCItjXCigQWUewTM/gCnXdK8XTkZuO5IUm29dYiYPQTBkMwRPxL8xhHYIv9CvjnLiuJkMWcBNnF0Hamr0upb16Dmz1/dnk3I5FxZQZu6LJlOzY/D6YEAa+Lfj3VB91I8A4Clv9e5YlXBnoW8abS3gfbH+DI6pk1ZcQKBgQCz0XO0Wb3BDH+neMO16b1HJUi72+aWTLu17BnqpfAdFs3SPjoAfJAG0uweiceii3AAHUaVY16OYKbf4d6lgOBhKfNUKvzg10V4muKfTocHAMz0QQFh+tweiRvOd5nHAjmjgLBlDi5icvVv8qj20XjLgc6uFWOm9+x4hKL7D2HVVQKBgDbzo1x3sM4CN8qyPt1p2dezsVzzMY2E1yP1En5rAHx6dMEV/p8Da3ROlJWOKDVUAvfKrqQE0dENB4uUQ/o+P6PncgaElASOOeTNZWS0YptzE9I/eROBEDrZIOhZbe+CmOp+vOjxyg3AwxMJq28HFmWMJAsaWuE+DJGp2H+5MyIxAoGBALPAmCJcZ1SY0u8tyK6LtrttKPUqEKqEkEx+dTXcpVfe2Obnb4HXAv7fUEwCvT7elpp2qX1idT5sncRF/RSC7UoT7ntf8aQtbfPvGMXZcR9uDHPKm8A/TlV0CAjwBBgtQEMSvMJ4V6PweStsbr00jsUYwOvC0/gv9AJRzL6eFNV1AoGAdMBi/JRird3JYHZtX31Q0dwTaNU6ObrbLlDVBCINZ77XXYSg9gBMSBy/FNGU1OphG7Ojb4S4hfQamnNL17YTnqipxVpMPdiGrDLlh9AvlIkjGrrwEBlHHLAJAAUaT90IhJnP/rBHgbcix0xEI1kpKEfqoe2MiH6XZQ9C16GtKJ0=";
		String trade_no;
		String notify_url = " http://192.168.0.101:8888";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
				"2016091100482839", APP_PRIVATE_KEY, "json", "utf-8",
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw1CWEhESaMMmkbgsivzqPDLvmwtEsNonEKwOlwGv2VSd5t2MUFCFwSW2aSLpZNnXmi6Iyq8+E9Em8CxJCqY75byp1Ljx4bdYt1BSugQ/JvWzHdkDGYpAZAvlYq+5ycCPdPEibkMhDn/xPjnrnWZje9K+34xXxfCaYgHgLqmTtwq8gcf402/B600XgIXjRrQWaBp/KYFyAKL328YYiuxv1CaIDSU3eX26W6WsOig4ENOe724aRRsd+6rzeVskGGGYAuW6+ZMMWKQhgbMKOE3GZIA4YU9N3QG4IUyMSDqyfLn/zK93KihSGLrk6xAYoxBBTjEGNIxOKb+QsuMil01kQQIDAQAB",
				"RSA2");

		// alipay
		if (payQrcodeCartRequest.getProvider() == 1) {
			
			// 生成订单二维码
			// AlipayClient alipayClient = new
			// DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
			// "2018022802289573", APP_PRIVATE_KEY, "json", "UTF-8",
			// ALIPAY_PUBLIC_KEY, "RSA2");
			AlipayTradePrecreateRequest Alipayrequest = new AlipayTradePrecreateRequest();// ����API��Ӧ��request��
			Alipayrequest.setBizContent("{" + "    \"out_trade_no\":" + "\"" + indent_id + "\"" + ","
					+ "    \"total_amount\":" + "\"" + Double.toString(totalprice) + "\"" + ","
					+ "    \"subject\":\"MixiusiCoffee\"," + "    \"store_id\":\"904\"," + "    \"notify_url\":" + "\""
					+ notify_url + "\"," + "    \"timeout_express\":\"90m\"}");// 设置业务参数
			try {
				AlipayTradePrecreateResponse Alipayresponse = alipayClient.execute(Alipayrequest);
				log.info("支付宝接口返回的请求体：" + Alipayresponse.getBody());
				log.info("支付宝接口返回的交易码：" + Alipayresponse.getOutTradeNo());

				payQrcodeCartResponce.setQrcodeUrl(Alipayresponse.getQrCode());

				payQrcodeCartResponce.getLinkFrame().serialId = payQrcodeCartRequest.getLinkFrame().serialId;

				if (null == p)
					payQrcodeCartResponce.getLinkFrame().resCode = 0;

				ctx.getChannel().write(payQrcodeCartResponce);

				System.out.println("二维码发送成功");

				// trade_no = Alipayresponse.

			} catch (AlipayApiException e) {
				log.error("alipayClient execute is error; "  + e.getMessage(), e);
			}

			// 查询支付状况

			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e) {

			}

			// AlipayClient aliQueryClient = new
			// DefaultAlipayClient
			// ("https://openapi.alipaydev.com/gateway.do","2016091100482839",APP_PRIVATE_KEY,"json","utf-8",
			// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw1CWEhESaMMmkbgsivzqPDLvmwtEsNonEKwOlwGv2VSd5t2MUFCFwSW2aSLpZNnXmi6Iyq8+E9Em8CxJCqY75byp1Ljx4bdYt1BSugQ/JvWzHdkDGYpAZAvlYq+5ycCPdPEibkMhDn/xPjnrnWZje9K+34xXxfCaYgHgLqmTtwq8gcf402/B600XgIXjRrQWaBp/KYFyAKL328YYiuxv1CaIDSU3eX26W6WsOig4ENOe724aRRsd+6rzeVskGGGYAuW6+ZMMWKQhgbMKOE3GZIA4YU9N3QG4IUyMSDqyfLn/zK93KihSGLrk6xAYoxBBTjEGNIxOKb+QsuMil01kQQIDAQAB","RSA2"
			// );
			AlipayTradeQueryRequest AliQueryrequest = new AlipayTradeQueryRequest();// 创建API对应的request类
			AliQueryrequest.setBizContent("{" + "\"out_trade_no\":" + "\"" + indent_id + "\"" +

					"}"); // 设置业务参数
			AlipayTradeQueryResponse AliQueryresponse;
			try {
				AliQueryresponse = alipayClient.execute(AliQueryrequest);
				System.out.print(AliQueryresponse.getBody());

				Payindent pi = payindentService.queryPayindentById(indent_id);
				Optional<Payindent> pay = Optional.ofNullable(pi);
				//支付成功
				if (AliQueryresponse.getTradeStatus() == "TRADE_SUCCESS" && pay.isPresent()) {
//					int flag2 = database.updateindentstatu(indent_id);
					//更新支付状态
					pay.get().setPayStatus(1);
//					payindentService.addPayindent(pi);
					PayNotifyResponce payNotifyResponce = new PayNotifyResponce(request.getLinkFrame().key);
					payNotifyResponce.setCoffeeindent(payQrcodeCartRequest.getCoffeeIndents());
					payNotifyResponce.getLinkFrame().resCode = 200;
					ctx.getChannel().write(payNotifyResponce);
				}else {
					pay.get().setPayStatus(2);
				}
				payindentService.addPayindent(pi);

			} catch (AlipayApiException e) {
				log.error(e.getMessage(), e);
			} // 通过alipayClient调用API，获得对应的response类

			// 若支付失败，则取消交易
			AlipayTradeCancelRequest Alicancelrequest = new AlipayTradeCancelRequest();
			Alicancelrequest.setBizContent("{" + "\"out_trade_no\":" + "\"" + indent_id + "\"" +

					"}"); // 设置业务参数
			AlipayTradeCancelResponse Alicanresponse;
			try {
				Alicanresponse = alipayClient.execute(Alicancelrequest);

				System.out.println(Alicanresponse.getBody());
				if (Alicanresponse.isSuccess()) {
					System.out.println("调用成功");
				} else {
					System.out.println("调用失败");
				}
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {// weixinpay

			MyConfig config = new MyConfig();
			WXPay wxpay = new WXPay(config);

			String Wxindent_id = cut(indent_id);

			Map<String, String> data = new HashMap<String, String>();
			data.put("body", "填写商品名称"); // 商品描述
			data.put("out_trade_no", Wxindent_id); // 商户订单号，不可重复
			data.put("device_info", ""); // 设备号
			data.put("fee_type", "CNY"); // 标价币种(默认人民币)
			data.put("total_fee", Double.toString(totalprice)); // 标价金额，单位：分
			data.put("spbill_create_ip", "192.168.0.106"); // 终端IP
			data.put("notify_url", "http://192.168.0.106:8888"); // 通知地址，必须是外网能访问的地址
			data.put("trade_type", "NATIVE"); // 此处指定为扫码支付
			data.put("product_id", "12"); // 商品ID
			data.put("nonce_str", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");// 随机字符串
			data.put("sign", "C380BEC2BFD727A4B6845133519F3AD6"); // 签名

			Map<String, String> respnoe = null;

			// 获得二维码
			try {
				respnoe = wxpay.unifiedOrder(data);
				String codeUrl = respnoe.get("code_url");

				payQrcodeCartResponce.setQrcodeUrl(codeUrl);
				System.out.println("返回的二维码url：" + codeUrl);

				payQrcodeCartResponce.getLinkFrame().serialId = payQrcodeCartRequest.getLinkFrame().serialId;

				if (null == p)
					payQrcodeCartResponce.getLinkFrame().resCode = 0;

				ctx.getChannel().write(payQrcodeCartResponce);

				System.out.println("二维码发送成功");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 查询订单
			/*
			 * long startTime=System.currentTimeMillis(); Timer timer = new
			 * Timer(); timer.schedule(new Task(startTime), 0, 5000);
			 */

			WXPay wxquery = new WXPay(config);

			Map<String, String> querydata = new HashMap<String, String>();

			querydata.put("out_trade_no", Wxindent_id); // 商户订单号，不可重复
			querydata.put("nonce_str", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");// 随机字符串
			querydata.put("sign", "C380BEC2BFD727A4B6845133519F3AD6"); // 签名

			Map<String, String> Queryrespnoe = null;
			try {
				Queryrespnoe = wxpay.orderQuery(querydata);
				String return_code = Queryrespnoe.get("return_code");

				if (return_code == "SUCCESS") {
					if (Queryrespnoe.get("result_code") == "SUCCESS") {
						// 若支付成功，则发送支付成功信息
						/*
						 * try { Thread.sleep(10 * 1000); } catch (Exception e)
						 * {
						 * 
						 * } int flag2 = database.updateindentstatu(indent_id);
						 * PayNotifyResponce payNotifyResponce = new
						 * PayNotifyResponce(request.getLinkFrame().key);
						 * payNotifyResponce.setCoffeeindent(
						 * payQrcodeCartRequest.getCoffeeIndents());
						 * payNotifyResponce.getLinkFrame().resCode = 200;
						 */
					}
					// 若支付失败，取消交易
					else {
						Queryrespnoe = wxpay.closeOrder(querydata);
						System.out.println(Queryrespnoe.get("return_code"));
					}

				}

				Queryrespnoe = wxpay.closeOrder(querydata);
				System.out.println(Queryrespnoe.get("return_code"));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private String cut(String indent_id) {
		// TODO Auto-generated method stub
		String newindent_id = "";
		for (int i = 0; i < indent_id.length(); i++) {
			if (indent_id.charAt(i) == ' ' || indent_id.charAt(i) == ':' || indent_id.charAt(i) == '.') {

			} else {
				newindent_id += indent_id.charAt(i);
			}

		}
		return newindent_id;
	}
}

//微信服务号信息，需要修改
class MyConfig implements WXPayConfig {

	// 公众账号ID
	public String getAppID() {
		return "wx37e0c08de855677a";
	}

	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	// 商户秘钥
	public String getKey() {
		return "1234567890";
	}

	// 商户号
	public String getMchID() {
		return "1234567890";
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
