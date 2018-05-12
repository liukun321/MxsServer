package com.mxs.mxsserver.protocol.responce;

import java.rmi.server.UID;

import com.mxs.mxsserver.protocol.LinkFrame;
import com.mxs.mxsserver.protocol.pack.AutoPackHelper;
import com.mxs.mxsserver.protocol.pack.Pack;


/**
 * User: jingege
 * Date: 1/13/12
 * Time: 10:21 AM
 */
public abstract class Responce extends AutoPackHelper{
    
    protected String uid;
    protected LinkFrame header = null;

    public Responce(String uid){
        
        this.uid = uid;
    }

    public LinkFrame getLinkFrame(){
    	if (header == null) {
    		header = new LinkFrame(getServiceId(),getCommandId(),uid);
    	}
        return header;
    }
    
    public Pack packResponce() {
    	return pack();
    }

    public abstract short getServiceId();

    public abstract short getCommandId();
   
}
