package com.cxc.tool;

import java.util.ArrayList;
import java.util.List;


public class DSqlPro {
	private  StringBuffer sb = null;
	private List<Object> paramList = null;
	
	public DSqlPro(){
		sb = new StringBuffer();
		paramList = new ArrayList<Object>();
	}

	public String getSql(){
		return sb.toString();
	}
	
	public Object[] getParamList(){
		return paramList.toArray();
	}
	
	public DSqlPro init(String sql, Object... params){
		sb = new StringBuffer(sql);
		paramList = new ArrayList<Object>();
		for(int i=0; i < params.length; i++){
			paramList.add(params[i]);
		}
		return this;
	}
	
	public DSqlPro append(String sql, Object... params){
		this.sb.append(sql);
		for(int i=0; i < params.length; i++){
			this.paramList.add(params[i]);
		}
		return this;
	}
}
