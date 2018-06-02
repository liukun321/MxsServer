package com.mxs.mxsserver.server;

import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.jboss.netty.channel.Channels.write;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxs.mxsserver.nettyserver.NioRequest;
import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ILinkService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.HandshakeResponce;

public class ServerHandler extends SimpleChannelHandler {
	private final Logger log = LoggerFactory.getLogger(ServerHandler.class);
	private DealRequest dealRequest;
	
	public static Map<String, Channel> channels = new ConcurrentHashMap();
	
	
	public ServerHandler() {
		dealRequest = new DealRequest();
	}

	/**
	 * 不管是否已经连接，都将执行
	 */

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);
		log.info("通信关闭----channelClosed");
	}

	/**
	 * 当网络连接时执行
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		log.info("连接成功---channelConnected");
		
	}

	/**
	 * 只有当网络连接过，断开时才会执行
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		log.info("连接断开-----channelDisconnected");
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		super.exceptionCaught(ctx, e);
		log.info("请求处理异常------exceptionCaught");
	}

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		log.info("------------接收请求开始-------------");
		if (e.getMessage() instanceof NioRequest) {
			NioRequest packet = (NioRequest) e.getMessage();

			// handle response
			requestReceived(ctx, packet);
		}
		// super.messageReceived(ctx, e);
		log.info("messageReceived");
		// 因为在设置管道工厂时，设置了StringDecode，所以在此时可以直接获得
		// 但如果没有设置的话,可以通过以下方法
		// ChannelBuffer message = (ChannelBuffer) e.getMessage();
		// System.out.println(new String(message.array()));

		// System.out.println(e.getMessage());

		/*
		 * 发送消息
		 */
		// ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi"
		// .getBytes());
		// ctx.getChannel().write(copiedBuffer);

		// 因为在管道中设置了encoder，所以可以像读取一样，写成下面的形式
		// ctx.getChannel().write("hi");

	}

	private void requestReceived(ChannelHandlerContext ctx, NioRequest request) {
		channels.put(request.getHeader().key, ctx.getChannel());
		log.info("-----------------requestReceived===开始------------");
		if (request.header.serviceId == ServiceID.SVID_LITE_LINK
				&& request.header.commandId == ILinkService.CommandId.CID_EXCHANGE_KEY) {
			// on ready
			log.info("-----------------验证交换密钥==开始------------");
			onReady(ctx, request);
		} else {
			// notify packet
			log.info("-----------------请求开始处理------------");
			dealRequest.dealRequest(request.header, request.body, ctx);
		}
	}

	private void onReady(ChannelHandlerContext ctx, NioRequest request) {
		HandshakeResponce responce = new HandshakeResponce("success");

		sendResponce(ctx, responce);
		/*
		 * packagePacker.setRc4Key(packagePacker.decryptRc4Key((responce.
		 * getRc4Key()))); packetDecoder.setUnpacker(packagePacker);
		 * packetEncoder.setPacker(packagePacker);
		 */

	}

	private void sendResponce(ChannelHandlerContext ctx, HandshakeResponce responce) {
		synchronized (this) {
			if (ctx.getChannel() != null) {
				log.info("-----------------处理请求并返回------------" + responce.getLinkFrame() + "---" + responce.getServiceId());
				ctx.getChannel().write(responce);
			}
		}
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// handle Pack
		if (e.getMessage() instanceof Pack) {
			// Pack to buffer

			Pack res = (Pack) e.getMessage();
			ChannelBuffer buffer = dynamicBuffer();
			buffer.writeBytes(res.getBuffer());

			// write buffer
			write(ctx, e.getFuture(), buffer);
		}
	}

}
