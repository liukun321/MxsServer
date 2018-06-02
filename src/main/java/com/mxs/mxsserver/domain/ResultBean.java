package com.mxs.mxsserver.domain;

import com.mxs.mxsserver.util.MxsConstants;

public class ResultBean<T> {
	private String code;
	private String message;
	private Object data;
	
	
	public ResultBean(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public ResultBean(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public ResultBean() {
	}
	
	public static ResultBean ok() {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS);
	}
	
	public static ResultBean ok(String msg) {
		return new ResultBean(MxsConstants.CODE0, msg);
	}
	
	public static ResultBean ok(Object data) {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS, data);
	}
	
	public static ResultBean error(String code, String message) {
		return new ResultBean(code, message, "");
	}
	
	public static ResultBean error() {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.ERROR, "");
	}
	public String getCode() {
		return code;
	}
	public String getmessage() {
		return message;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public void setmessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
