package com.mxs.mxsserver.handler;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.protocol.request.KeepAliveRequest;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.KeepAliveResponce;

//保持连接
@Component
public class KeepAliveRequestHandle extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(KeepAliveRequestHandle.class);
	public static Map<String, Boolean> map = new HashMap();//保存对当前登陆的咖啡机信息
	private String machineId;
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		KeepAliveRequest keepAliveRequest = (KeepAliveRequest)request;
		machineId = keepAliveRequest.getLinkFrame().key;
		Boolean flag = map.get(machineId);
		if(null == flag) {//没有登陆过则新增
			map.put(machineId, true);
		}else {
			if(flag) {
				map.put(machineId, false);
			}else {
				map.put(machineId, true);
			}
			
		}
		log.info("----心跳检测------");
		KeepAliveResponce keepAliveResponce = new KeepAliveResponce(machineId);
		ChannelFuture cf = ctx.getChannel().write(keepAliveResponce);
	}

}
