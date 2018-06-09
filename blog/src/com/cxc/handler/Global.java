package com.cxc.handler;

import java.net.URL;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class Global {
private static Prop gProp = null;
	
	static
	{
		//优先加载 global-test.properties 文件
		URL url = Thread.currentThread().getContextClassLoader().getResource("global-test.properties");
		if(url != null)
			gProp = PropKit.use("global-test.properties");
		else
			gProp = PropKit.use("global.properties");
	}
	
	private static ThreadLocal<Context> currentContext 
						= new ThreadLocal<Context>();
	
	/**
	 * 设置请求上下文对象
	 * @return
	 */
	public static void setContext(Context context)
    {
        currentContext.set(context);
    }
	
	/**
	 * 获取请求上下文对象
	 * @return
	 */
	public static Context getContext()
    {
        Context context = currentContext.get();
        return context;
    }
	
	
	/**
	 * 根据name获取session值
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSession(String name)
	{
		return (T) getContext().getSession().getAttribute(name);
	}
	
	/**
	 * 设置session
	 * @param name
	 * @param value
	 */
	public static void setSession(String name,Object value)
	{
		getContext().getSession().setAttribute(name, value);
	}
	
	/**
	 * 是否debug模式
	 * @return
	 */
	public static boolean isDebug(){
		return "true".equalsIgnoreCase(gProp.get("debug"));
	}
	
	/**
	 * 获取global.properties中的配置
	 * @return
	 */
	public static String getProp(String key){
		return gProp.get(key);
	}
	
	/**
	 * 获取global.properties中的配置
	 * @return
	 */
	public static String getProp(String key,String defaultStr){
		return gProp.get(key,defaultStr);
	}
}
