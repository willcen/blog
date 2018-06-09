package org.cxc.controller;

import com.cxc.config.CController;
import com.jfinal.core.Controller;

@CController(controllerkey="/test")
public class TestController extends Controller{
	public void index(){
		renderText("test");
	}
	public void aa(){
		renderText("-------------");
	}

}
