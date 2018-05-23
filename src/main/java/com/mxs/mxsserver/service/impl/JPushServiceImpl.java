package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.service.JPushService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
@Service
public class JPushServiceImpl implements JPushService {

	private final static String APPKET = "44262636e2afd75d9b9f7932";
	 
    private final static String MASTERSECRET = "ae5c0ab5f093b2aba1f8ce25";
 
    private static JPushClient jPushClient = new JPushClient(MASTERSECRET, APPKET);//通知默认保留24小时。
	
	@Override
	public int sendToRegistrationId(String registrationId, String notification_alert, String notification_title,
			String extrasparam) {
		int result = 0;
        try {
            PushPayload pushPayload= JPushServiceImpl.buildPushObjectWithRegistrationId(registrationId, 
            		notification_alert, notification_title, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        
         return result;
	}

	@Override
	public int sendToAll(String notification_alert, String notification_title, String extrasparam) {
		int result = 0;
        try {
            PushPayload pushPayload= JPushServiceImpl.buildPushObjectWithAll(notification_alert, 
            		notification_title, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            //System.out.println(pushResult);
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        
         return result;
	}
	
	@Override
	public int sendToAllIos(String notification_alert, String notification_title, String extrasparam) {
		
		int result = 0;
        try {
            PushPayload pushPayload= JPushServiceImpl.buildPushObjectWithIos(notification_alert, 
            		notification_title, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            //System.out.println(pushResult);
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        
         return result;
	}

	@Override
	public int sendToAllAndroid(String notification_alert, String notification_title, String extrasparam) {
		
		int result = 0;
        try {
            PushPayload pushPayload= JPushServiceImpl.buildPushObjectWithAndroid(notification_alert, 
            		notification_title, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            //System.out.println(pushResult);
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        
         return result;
	}

	/**
	 * 建立以唯一设备标识符推送的对象
	 * @param registrationId  唯一设备标识
	 * @param notification_alert  通知内容
	 * @param notification_title  通知标题
	 * @param extrasparam  扩展字段
	 * @return  返回推送对象
	 */
	private static PushPayload buildPushObjectWithRegistrationId(String registrationId, String notification_alert, String notification_title,
			String extrasparam) {
		return PushPayload.newBuilder()
	            //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	            .setPlatform(Platform.all())
	            //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	            .setAudience(Audience.registrationId(registrationId))
	            //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	            .setNotification(Notification.newBuilder()
	            //指定当前推送的android通知
	            .addPlatformNotification(AndroidNotification.newBuilder()
	            	.setAlert(notification_alert)   //设置通知内容（必填）
	                .setTitle(notification_title)	//设置通知标题（可选）
	                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                .addExtra("androidNotification extras key",extrasparam)
	                .build())
	                
	            //指定当前推送的iOS通知
	            .addPlatformNotification(IosNotification.newBuilder()
	                //传一个IosAlert对象，指定apns title、title、subtitle等
	                .setAlert(notification_alert)
	                //直接传alert
	                //此项是指定此推送的badge（应用角标）自动加1
	                .incrBadge(1)
	                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                .setSound("sound.caf")
	                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                .addExtra("iosNotification extras key",extrasparam)
	                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                //取消此注释，消息推送时ios将无法在锁屏情况接收
	                // .setContentAvailable(true)
	                .build())
	            
	            //指定当前推送的winPhone通知
	            /*.addPlatformNotification(WinphoneNotification.newBuilder()
	              	.setAlert(notification_alert)
	              	//.setTitle(""))  //设置通知标题（可选）此标题将取代显示app名称的地方
	                .build())*/
	            	.build())
	            	.build();
	}
	
	/**
	 * 建立推送所有用户的推送对象
	 * @param notification_alert  通知内容
	 * @param notification_title  通知标题
	 * @param extrasparam  扩展字段
	 * @return  返回推送对象
	 */
	private static PushPayload buildPushObjectWithAll(String notification_alert, 
			String notification_title, String extrasparam) {
		return PushPayload.newBuilder()
	            //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	            .setPlatform(Platform.all())
	            //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	            .setAudience(Audience.all())
	            //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	            .setNotification(Notification.newBuilder()
	            //指定当前推送的android通知
	            .addPlatformNotification(AndroidNotification.newBuilder()
	            	.setAlert(notification_alert)   //设置通知内容（必填）
	                .setTitle(notification_title)	//设置通知标题（可选）
	                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                .addExtra("androidNotification extras key",extrasparam)
	                .build())
	                
	            //指定当前推送的iOS通知
	            .addPlatformNotification(IosNotification.newBuilder()
	                //传一个IosAlert对象，指定apns title、title、subtitle等
	                .setAlert(notification_alert)
	                //直接传alert
	                //此项是指定此推送的badge（应用角标）自动加1
	                .incrBadge(1)
	                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                .setSound("sound.caf")
	                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                .addExtra("iosNotification extras key",extrasparam)
	                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                //取消此注释，消息推送时ios将无法在锁屏情况接收
	                // .setContentAvailable(true)
	                .build())
	            
	            //指定当前推送的winPhone通知
	            /*.addPlatformNotification(WinphoneNotification.newBuilder()
	              	.setAlert(notification_alert)
	              	//.setTitle(""))  //设置通知标题（可选）此标题将取代显示app名称的地方
	                .build())*/
	            	.build())
	            	.build();
	}
	
	/**
	 * 建立推送所有ios用户的推送对象
	 * @param notification_alert  通知内容
	 * @param notification_title  通知标题
	 * @param extrasparam  扩展字段
	 * @return  返回推送对象
	 */
	private static PushPayload buildPushObjectWithIos(String notification_alert, 
			String notification_title, String extrasparam) {
		return PushPayload.newBuilder()
	            //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	            .setPlatform(Platform.ios())
	            //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	            .setAudience(Audience.all())
	            //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	            .setNotification(Notification.newBuilder()
	                
	            //指定当前推送的iOS通知
	            .addPlatformNotification(IosNotification.newBuilder()
	                //传一个IosAlert对象，指定apns title、title、subtitle等
	                .setAlert(notification_alert)
	                //直接传alert
	                //此项是指定此推送的badge（应用角标）自动加1
	                .incrBadge(1)
	                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                .setSound("sound.caf")
	                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                .addExtra("iosNotification extras key",extrasparam)
	                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                //取消此注释，消息推送时ios将无法在锁屏情况接收
	                // .setContentAvailable(true)
	                .build())
	            	.build())
	            	.build();
	}
	
	/**
	 * 建立推送所有安卓用户的推送对象
	 * @param notification_alert  通知内容
	 * @param notification_title  通知标题
	 * @param extrasparam  扩展字段
	 * @return  返回推送对象
	 */
	private static PushPayload buildPushObjectWithAndroid(String notification_alert, 
			String notification_title, String extrasparam) {
		return PushPayload.newBuilder()
	            //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	            .setPlatform(Platform.android())
	            //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	            .setAudience(Audience.all())
	            //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	            .setNotification(Notification.newBuilder()
		            //指定当前推送的android通知
		            .addPlatformNotification(AndroidNotification.newBuilder()
		            	.setAlert(notification_alert)   //设置通知内容（必填）
		                .setTitle(notification_title)	//设置通知标题（可选）
		                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
		                .addExtra("androidNotification extras key",extrasparam)
		                .build())
		            	.build())
		            	.build();
	}

	

}
