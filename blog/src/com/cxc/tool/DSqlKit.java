package com.cxc.tool;


/**
 * Âä®Ê?ÅSQLËØ≠Âè•ÊûÑÈ?†Â∑•ÂÖ?
 * @author dkomj
 *
 */
public class DSqlKit {
	private static ThreadLocal<DSqlPro> proLocal = new ThreadLocal<DSqlPro>();

	private static DSqlPro getPro(){
		DSqlPro pro = proLocal.get();
		if(pro == null){
			pro = new DSqlPro();
			proLocal.set(pro);
		}
		
		return pro;
	}
	
	public static String getSql(){
		return getPro().getSql();
	}
	
	public static Object[] getParamList(){
		return getPro().getParamList();
	}
	
	public static DSqlPro init(String sql, Object... params){
		return getPro().init(sql,params);
	}
	
	public static DSqlPro append(String sql, Object... params){
		return getPro().append(sql,params);
	}

}

