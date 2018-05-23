//package com.mxs.mxsserver.listener;
//
//import java.util.Date;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import com.mxs.mxsserver.domain.CoffeeMachine;
//import com.mxs.mxsserver.handler.KeepAliveRequestHandle;
//import com.mxs.mxsserver.service.CoffeeMachineService;
//import com.mxs.mxsserver.util.StringUtils;  
//  
//@Component
//public class KeepAliveListener implements ApplicationListener<ContextRefreshedEvent>{
//	 	private Logger log = LoggerFactory.getLogger(KeepAliveListener.class);
//	 	private boolean flag = true;
//	 	
//		public boolean isFlag() {
//			return flag;
//		}
//		public void setFlag(boolean flag) {
//			this.flag = flag;
//		}
//		@Autowired
//		private CoffeeMachineService service;
//		private static CoffeeMachineService coffeeMachineService;
//		@PostConstruct
//		public void init() {
//			coffeeMachineService = service;
//		}
//	    @Override
//	    public void onApplicationEvent(ContextRefreshedEvent event) {  
//	        log.info("加载系统配置...");  
//	          
//	        KeepAliveRequestHandle keepAliveRequestHandle = event.getApplicationContext().getBean(KeepAliveRequestHandle.class);
//	        String machineId = keepAliveRequestHandle.getMachineId();
//	        if(StringUtils.isNull(machineId)) {
//	        	log.info("还无法获取机器的ID");
//	        }else {
//	        	CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineId);
//	        	while(true) {
//	        		try {
//						Thread.sleep(10000);
//						Map<String, Boolean> map = keepAliveRequestHandle.map;
//						if(flag != map.get(machineId)) {//不等，则说明心跳请求被调用
//							flag = map.get(machineId);//重置监听器的标志位
//						}else {
//							//认为当前机器停机
//							coffeeMachine.setDownTime(new Date());
//							coffeeMachine.setIs_running(false);
//							coffeeMachineService.addCoffeeMachine(coffeeMachine);
//						}
//					} catch (InterruptedException e) {
//						log.error(e.getMessage());
//					}
//	        		break;
//	        	}
//	        }
//	        log.info("系统配置加载完成...");  
//	    }  
//}
