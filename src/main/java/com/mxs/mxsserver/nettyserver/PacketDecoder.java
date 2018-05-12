package com.mxs.mxsserver.nettyserver;

import java.nio.ByteBuffer;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.protocol.LinkFrame;
import com.mxs.mxsserver.protocol.PacketHeader;
import com.mxs.mxsserver.protocol.pack.PackagePacker;
import com.mxs.mxsserver.protocol.pack.PacketCompressor;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.util.HexDump;



public class PacketDecoder extends AbstractPackDecoder {

	private PackagePacker unpacker;
	
	public PacketDecoder() {
	}

	public Object parsePacket(ChannelHandlerContext ctx, byte[] packet) throws Exception 
	{
		
		Unpack up = new Unpack(packet);
	
		PacketHeader header = new PacketHeader();
		up.popMarshallable(header);
		LinkFrame lf = header.toOldStyleHeader();

		System.out.println("received " + header);
		// 如果是被压缩的大包，解压
		if (lf.isCompressed()) {
			ByteBuffer uncompressed = PacketCompressor.uncompress(up);
			up = new Unpack(uncompressed);
		}
		NioRequest p = new NioRequest();
		p.header = lf;
		p.body = up;
		
		return p;

	}

	public byte[] decrypt(byte[] src) {
		Unpack up = new Unpack(src);
		up = unpacker.decrypt(up);
		return up.getBuffer().array();
	}
	
	public void setUnpacker(PackagePacker unpacker) {
		this.unpacker = unpacker;
	}
	
	public void reset() {
		packetSize = -1;
	}
}
