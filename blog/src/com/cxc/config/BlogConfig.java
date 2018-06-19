package com.cxc.config;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
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
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

public class BlogConfig extends JFinalConfig{
	
	@Override
	public void afterJFinalStart() {
		FreeMarkerRender.setTemplateLoadingPath("/view");
		FreeMarkerRender.setProperty("template_update_delay", "0");
        FreeMarkerRender.setProperty("classic_compatible", "true");
        FreeMarkerRender.setProperty("whitespace_stripping", "true");
        FreeMarkerRender.setProperty("date_format", "yyyy-MM-dd");
        FreeMarkerRender.setProperty("time_format", "HH:mm:ss");
        FreeMarkerRender.setProperty("datetime_format", "yyyy-MM-dd HH:mm");
        FreeMarkerRender.setProperty("default_encoding", "UTF-8");
	}

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JFINAL_TEMPLATE);
	}
	
	@Override
	public void configRoute(Routes me) {
		me.setBaseViewPath("/view");
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
		loadDb(me);
	}

	
	private void loadDb(Plugins me) {
        //数据源加载
        String ds = PropKit.use("global.properties").get("db.datasources");
        for (String dbname : ds.split(",")) {
            String dbKey = dbname + ".";
            String url = PropKit.get(dbKey + "url");
            String username = PropKit.get(dbKey + "username");
            String password = PropKit.get(dbKey + "password");
            String driver_class = PropKit.get(dbKey + "driver_class");
            int initialSize = PropKit.getInt(dbKey + "initialSize");
            int minIdle = PropKit.getInt(dbKey + "minIdle");
            int maxActive = PropKit.getInt(dbKey + "maxActive");
            boolean showSql = "true".equalsIgnoreCase(PropKit.get(dbKey + "showSql"));
            //boolean toLowerCase = PropKit.getBoolean(dbKey + "record_to_json_lowercase", true);

            DruidPlugin dp = new DruidPlugin(url, username, password);
            dp.set(initialSize, minIdle, maxActive);
            dp.setDriverClass(driver_class);

            String dbType = null;
            Dialect dialect = null;
            if (url.toLowerCase().contains(JdbcConstants.ORACLE)) {
                dbType = JdbcConstants.ORACLE;
                dialect = new OracleDialect();
                dp.setValidationQuery("select 1 from dual");
            } else if (url.toLowerCase().contains(JdbcConstants.SQL_SERVER)) {
                dbType = JdbcConstants.SQL_SERVER;
                dialect = new AnsiSqlDialect();
            } else if (url.toLowerCase().contains(JdbcConstants.MYSQL)) {
                dbType = JdbcConstants.MYSQL;
                dialect = new MysqlDialect();
            } else if (url.toLowerCase().contains(JdbcConstants.POSTGRESQL)) {
                dbType = JdbcConstants.POSTGRESQL;
                dialect = new PostgreSqlDialect();
            } else {
                throw new RuntimeException("不支持的数据库！");
            }

            dp.addFilter(new StatFilter());
            me.add(dp);

            ActiveRecordPlugin arp = new ActiveRecordPlugin(dbname, dp);
            arp.setShowSql(showSql);
            arp.setDialect(dialect);
            //arp.setContainerFactory(new CaseInsensitiveContainerFactory(toLowerCase));

            //添加SQL模板支持
//            String sqlTemplatePath = PathKit.getRootClassPath() + File.separator + "sql" + File.separator + "templates";
//            arp.setBaseSqlTemplatePath(sqlTemplatePath);
//            File[] files = new File(sqlTemplatePath).listFiles((File pathname) -> pathname.getName().endsWith(".sql"));
//            for (File file : files) {
//                arp.addSqlTemplate(file.getName());
//            }

            //添加自定义SQL模板
//            arp.getEngine()
//                    .addDirective("decode", SqlDecodeDirective.class)
//                    .addDirective("fieldSelector", FieldSelectorDirective.class)
//                    .addDirective("paraLike", ParaLikeDirective.class)     
//                    .addSharedObject("strKit", new com.jfinal.kit.StrKit())
//                    .addSharedObject("pramUtil", new com.blit.blit_jlzz.utils.PramUtil());

            me.add(arp);
        }
    }
	

}
