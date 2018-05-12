package com.mxs.mxsserver.nettyserver;

import com.mxs.mxsserver.protocol.LinkFrame;
import com.mxs.mxsserver.protocol.pack.Unpack;


public class NioRequest {
	public LinkFrame header ;
	public Unpack body;
}
