package com.cxc.app;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;

public class App {
	public static void main(String[] args) {
		JFinal.start("WebContent",8080,"/",3);
	}

}
