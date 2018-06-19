package com.cxc.controller;

import com.cxc.config.CController;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

@CController(controllerkey="/admin")
public class AdminController extends Controller{
	
	public void index(){
		renderTemplate("/admin/index.html");
	}
	
	public void renderMain(){
		renderTemplate("/admin/main.html");
	}
	
	public void articleList(){
		renderTemplate("/adminRes/page/news/articleList.html");
	}
	
	public void xxx(){
		Record re = new Record();
		re.set("id", "1");
		re.set("username", "Lay");
		
		renderJson(re);
	}

}
