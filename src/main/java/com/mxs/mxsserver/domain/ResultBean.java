package com.mxs.mxsserver.domain;

import java.util.List;

import com.mxs.mxsserver.util.MxsConstants;

public class ResultBean<T> {
	private String code;
	private String msg;
	private Object data;
	
	
	public ResultBean(String code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public ResultBean(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public ResultBean() {
	}
	
	public static ResultBean ok() {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS);
	}
	
	public static ResultBean ok(Object data) {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS, data);
	}
	
	public static ResultBean error(String code, String msg) {
		return new ResultBean(code, msg);
	}
	
	public static ResultBean error() {
		return new ResultBean(MxsConstants.CODE1, MxsConstants.ERROR);
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
