package com.mxs.mxsserver.nettyserver;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.pack.PackagePacker;
import com.mxs.mxsserver.protocol.responce.Responce;;



public class PacketEncoder extends OneToOneEncoder {
	
	PackagePacker packer;
	
	public PacketEncoder() {
	}
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		// TODO Auto-generated method stub
		
		if(msg instanceof Responce)
		{
			Responce req = (Responce)msg;
			
			Pack p = packer.packRequest(req);
			return p;
		}
		else
		{
			return msg;
		}
	}

	public void setPacker(PackagePacker packer) {
		this.packer = packer;
	}
	
	public void reset() {
		
	}
}
