package com.mxs.mxsserver.protocol.marshal;

import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.pack.Unpack;

public class Strmable implements Marshallable {
	public String string = "";

	public Strmable(String str) {
		string = str;
	}

	public Strmable() {
		string = "";
	}

	public void marshal(Pack p) {
		p.putVarstr(string);
	}

	public void unmarshal(Unpack up) {
		string = up.popVarstr();
	}
}
