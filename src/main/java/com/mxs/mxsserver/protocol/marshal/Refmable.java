package com.mxs.mxsserver.protocol.marshal;

import java.nio.ByteBuffer;

import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.pack.Unpack;


public class Refmable implements Marshallable {
	public ByteBuffer buffer = null;

	public Refmable() {
	}

	public Refmable(ByteBuffer src) {
		buffer = src;
	}

	public void marshal(Pack p) {
		buffer.rewind();
		p.putBuffer(buffer);
	}

	public void unmarshal(Unpack p) {
		buffer.rewind();
		buffer = p.getBuffer();
	}
}
