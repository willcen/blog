package com.cxc.config;

import java.util.Iterator;
import java.util.Set;

import com.cxc.handler.ContextHandler;
import com.cxc.tool.ClassScanKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;

public class BlogConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}
	
	@Override
	public void configRoute(Routes me) {
		String packages = PropKit.use("global.properties").get("package.controllers");
        for (String p : packages.split(",")) {
            Set<Class<?>> set = ClassScanKit.getClasses(p);
            Iterator<Class<?>> it = set.iterator();
            while (it.hasNext()) {
                Class<?> cls = it.next();
                if (Controller.class.isAssignableFrom(cls)) {
                    CController ctrlAnnotation = cls.getAnnotation(CController.class);
                    if (ctrlAnnotation != null) {
                        me.add(ctrlAnnotation.controllerkey(), (Class<? extends Controller>) cls);
                    }
                }
            }
        }
	}

	@Override
	public void configEngine(Engine me) {
		me.setBaseTemplatePath(PathKit.getWebRootPath() + "/view");
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		
	}

	

}
