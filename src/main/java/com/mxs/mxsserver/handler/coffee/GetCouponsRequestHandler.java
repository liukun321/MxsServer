package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mxs.mxsserver.domain.Coupons;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.GetCouponsRequest;
import com.mxs.mxsserver.protocol.responce.coffee.GetCouponsResponce;
import com.mxs.mxsserver.service.CouponsService;
import com.mxs.mxsserver.util.StringUtils;

/**
 * 查询优惠券信息
 * @author liukun
 *
 */
@Component
public class GetCouponsRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetCouponsRequestHandler.class);
	@Autowired
	private CouponsService dservice;
	private static CouponsService discountInfoService;
	@PostConstruct
	public void init() {
		discountInfoService = dservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		GetCouponsRequest getCouponsRequest = (GetCouponsRequest)request;
		String couponCode = getCouponsRequest.getCouponCode();
		log.info(getCouponsRequest.getCouponCode() + "--" + getCouponsRequest.getLinkFrame().resCode + "===" + request.getLinkFrame().serialId);
		System.out.println(request.getLinkFrame().key);
		GetCouponsResponce getCouponsResponce = new GetCouponsResponce(request.getLinkFrame().key);
		getCouponsResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		JSONObject jsonObj = new JSONObject();
		if(!StringUtils.isNull(couponCode)) {
			Coupons result = discountInfoService.queryCouponsByCode(couponCode);
			if(null != result) {
				log.info(result.toString());
				String value = result.getValue();
				String discount = result.getDiscount();
				jsonObj.put("value", value);
				jsonObj.put("discount", discount);
			}else {
				jsonObj.put("value", null);
				jsonObj.put("discount", null);
			}
		}
		getCouponsResponce.setFavorable(jsonObj.toString());
		ctx.getChannel().write(getCouponsResponce);

	}
}
