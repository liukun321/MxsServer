package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mxs.mxsserver.domain.MobilePay;
import com.mxs.mxsserver.domain.Pickup;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PickupRequest;
import com.mxs.mxsserver.service.MobilePayService;
import com.mxs.mxsserver.service.PickupService;
/**
 * 取货请求
 * @author liukun
 *
 */
public class PickupRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(PickupRequestHandler.class);
	@Autowired
	private PickupService pservice;
	private static PickupService pickupService;
	
	@Autowired
	private MobilePayService mservice;
	private static MobilePayService mobilePayService;
	@PostConstruct
	public void init() {
		pickupService = pservice;
		mobilePayService = mservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("------------开始取货请求-----------");
		PickupRequest pickupRequest = (PickupRequest)request;
		String pickupCode = pickupRequest.getPickupCode();
		
		Pickup pk = pickupService.queryPickup(pickupCode);
		
		MobilePay mp = mobilePayService.queryMobilePay(pk.getIndentId());
		
		log.info(mp.toString());
		
		
		
		
		
		
		
		
		
	}

}
