package com.cxc.controller;

import java.util.ArrayList;
import java.util.List;

import com.cxc.config.CController;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;

@CController(controllerkey="/")
public class IndexController extends Controller{
	public void index(){
		
		Record re = new Record();
		re.set("id", "1");
		re.set("typeName", "java");
		Record re2 = new Record();
		re2.set("id", "2");
		re2.set("typeName", "javaScript");
		Record re3 = new Record();
		re3.set("id", "3");
		re3.set("typeName", "Mysql");
		Record re4 = new Record();
		re4.set("id", "4");
		re4.set("typeName", "oracle");
		
		List<Record> list = new ArrayList<Record>();
		list.add(re);
		list.add(re2);
		list.add(re3);
		list.add(re4);
		setAttr("list",list);
		renderTemplate("/index/index.html");
	}
}

