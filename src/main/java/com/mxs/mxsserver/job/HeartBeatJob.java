package com.mxs.mxsserver.job;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.protocol.responce.KeepAliveResponce;
import com.mxs.mxsserver.server.ServerHandler;
import com.mxs.mxsserver.service.CoffeeMachineService;

@Component
public class HeartBeatJob {
	private final Logger log = LoggerFactory.getLogger(HeartBeatJob.class);
	
	@Autowired
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
	}
	
	@Scheduled(cron = "0 0/5 * * * ?")//每5分钟执行一次
	public void sendHeartBeat() {
		log.info("--------开始检测客户端的心跳---------");
		Map<String, Channel> map = ServerHandler.channels;
		for(Entry<String, Channel> entry : map.entrySet()) {
			String machineId = entry.getKey();
			log.info("咖啡机" + machineId + "正在进行心跳检测");
			CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineId);
			KeepAliveResponce keepAliveResponce = new KeepAliveResponce(machineId);
			ChannelFuture cf = entry.getValue().write(keepAliveResponce);
			Throwable error = cf.getCause();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
			if(null == error) {
				//客户端停机,将连接从map中删除
				map.remove(entry.getKey());
				coffeeMachine.setDownTime(new Date());
				coffeeMachine.setIs_running(false);
				coffeeMachineService.addCoffeeMachine(coffeeMachine);
			}
		}
		
	}
}
