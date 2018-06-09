package com.cxc.handler;

import java.net.URL;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class Global {
private static Prop gProp = null;
	
	static
	{
		//���ȼ��� global-test.properties �ļ�
		URL url = Thread.currentThread().getContextClassLoader().getResource("global-test.properties");
		if(url != null)
			gProp = PropKit.use("global-test.properties");
		else
			gProp = PropKit.use("global.properties");
	}
	
	private static ThreadLocal<Context> currentContext 
						= new ThreadLocal<Context>();
	
	/**
	 * �������������Ķ���
	 * @return
	 */
	public static void setContext(Context context)
    {
        currentContext.set(context);
    }
	
	/**
	 * ��ȡ���������Ķ���
	 * @return
	 */
	public static Context getContext()
    {
        Context context = currentContext.get();
        return context;
    }
	
	
	/**
	 * ����name��ȡsessionֵ
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSession(String name)
	{
		return (T) getContext().getSession().getAttribute(name);
	}
	
	/**
	 * ����session
	 * @param name
	 * @param value
	 */
	public static void setSession(String name,Object value)
	{
		getContext().getSession().setAttribute(name, value);
	}
	
	/**
	 * �Ƿ�debugģʽ
	 * @return
	 */
	public static boolean isDebug(){
		return "true".equalsIgnoreCase(gProp.get("debug"));
	}
	
	/**
	 * ��ȡglobal.properties�е�����
	 * @return
	 */
	public static String getProp(String key){
		return gProp.get(key);
	}
	
	/**
	 * ��ȡglobal.properties�е�����
	 * @return
	 */
	public static String getProp(String key,String defaultStr){
		return gProp.get(key,defaultStr);
	}
}
