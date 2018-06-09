package com.cxc.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Context {

	private ServletContext servletContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public Context(ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
	{
		this.setServletContext(servletContext);
		this.setResponse(response);
		this.setRequest(request);
	}


	public ServletContext getServletContext() {
		return servletContext;
	}


	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public HttpServletResponse getResponse() {
		return response;
	}


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


	public HttpSession getSession() {
		return request.getSession();
	}
}
