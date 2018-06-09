package com.cxc.controller;

import com.cxc.config.CController;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

@CController(controllerkey="/")
public class IndexController extends Controller{
	public void index(){
		renderTemplate("/index/index.html");
	}
	
}

