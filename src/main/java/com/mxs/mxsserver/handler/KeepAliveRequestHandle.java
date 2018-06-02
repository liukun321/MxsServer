package com.mxs.mxsserver.handler;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.ErrorRecord;
import com.mxs.mxsserver.protocol.request.KeepAliveRequest;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.KeepAliveResponce;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.ErrorRecordService;
import com.mxs.mxsserver.util.DateUtils;

/**
 * 保持连接
 * @author liukun
 * 
 * 保持会话的请求：确定保持连接，同时更新机器的运行状态
 */
@Component
public class KeepAliveRequestHandle extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(KeepAliveRequestHandle.class);
	@Autowired
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	@Autowired
	private ErrorRecordService eservice;
	private static ErrorRecordService errorRecordService;
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		errorRecordService = eservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("---------------保持会话请求-----------------");
		KeepAliveRequest keepAliveRequest = (KeepAliveRequest)request;
		
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(keepAliveRequest.getLinkFrame().key);
		//是否判断查询的结果是否为空
		if(null != cm) {
			if(cm.getIs_running()) {
				
			}else {//上一次是停机状态，更新咖啡机状态，停机时间保留，知道下次停机时间发生，才更新停机时间
				cm.setIs_running(true);
				coffeeMachineService.addCoffeeMachine(cm);
				//终止停机错误的记录  怎样定位指定的错误记录
				List<ErrorRecord> ers = errorRecordService.queryErrorRecord(cm.getMachineId());
				ers.stream().forEach(error -> {
					Date end = new Date();
					error.setEndTime(end);
					error.setDurationTime(DateUtils.subtractForDate(error.getStartTime(), end, 1000*60));
				});
				errorRecordService.batchUpdate(ers);
			}
		}else {
			log.info(keepAliveRequest.getLinkFrame().key + "该咖啡机在数据库中不存在了！！");
		}
		log.info("----心跳检测------");
		KeepAliveResponce keepAliveResponce = new KeepAliveResponce(keepAliveRequest.getLinkFrame().key);
		ctx.getChannel().write(keepAliveResponce);
	}

}
