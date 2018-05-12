package com.mxs.mxsserver.protocol.marshal;

import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.pack.Unpack;

public interface Marshallable {
	public abstract void marshal(Pack pack);

	public abstract void unmarshal(Unpack unpack);
}
