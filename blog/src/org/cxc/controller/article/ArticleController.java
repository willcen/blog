package org.cxc.controller.article;

import com.cxc.config.CController;
import com.jfinal.core.Controller;

@CController(controllerkey="/article")
public class ArticleController extends Controller{
	
	public void index(){
		renderTemplate("/article/article.html");
	}

}
