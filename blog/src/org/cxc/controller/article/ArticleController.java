package org.cxc.controller.article;

import com.cxc.config.CController;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

@CController(controllerkey="/article")
public class ArticleController extends Controller{
	
	public void index(){
		renderTemplate("/article/article.html");
	}
	
	public void articleMx(){
		renderTemplate("/article/articleMx.html");
	}

}
