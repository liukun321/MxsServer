package com.mxs.mxsserver.nettyserver;

import com.mxs.mxsserver.protocol.LinkFrame;
import com.mxs.mxsserver.protocol.pack.Unpack;


public class NioRequest {
	public LinkFrame header ;
	public Unpack body;
	public LinkFrame getHeader() {
		return header;
	}
	public Unpack getBody() {
		return body;
	}
	public void setHeader(LinkFrame header) {
		this.header = header;
	}
	public void setBody(Unpack body) {
		this.body = body;
	}
	
	
}
