package com.cxc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;

public class ContextHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
request.setAttribute("g_path", request.getContextPath());
		
		HttpServletRequest newReq = request;
		
		//设置全局请求上下文
		Context txt = new Context(JFinal.me().getServletContext(),newReq,response);
		Global.setContext(txt);
		int index = target.indexOf(".");
	    if (index == -1){
		    next.handle(target, request, response, isHandled);
	    } else {
	    	isHandled[0] = false;
	    	return;
	    }
	}

}
