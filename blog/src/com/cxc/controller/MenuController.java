package com.cxc.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxc.config.CController;
import com.cxc.tool.DSqlKit;
import com.cxc.utils.CreateUUID;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

@CController(controllerkey = "/menu")
public class MenuController extends Controller {

	public void index() {
		renderNull();
	}

	public void view_menu() {
		renderTemplate("/admin/page/Menu.html");
	}

	public void add_menu() {
		String pid = getPara("pid");
		String iconFa = getPara("iconFa");
		String menuName = getPara("menuName");
		String url = getPara("url");
		String id = CreateUUID.getUUID();
		String sql = "INSERT INTO SYS_MENU(ID,MENUNAME,URL,PID,ICONFA) VALUES(?,?,?,?,?)";
		Db.use("main").update(sql, id, menuName, url, pid, iconFa);
		renderJson(Ret.ok("isOk", true));
	}

	@Before(Tx.class)
	public void del() {
		String[] ids = getParaValues("ids[]");
		if (ids.length > 0) {
			for (String id : ids) {
				Db.update("DELETE FROM SYS_MENU WHERE ID = ?", id);
			}
		}
		renderJson(Ret.ok("isOk", true));
	}

	public void list() {
		String select = loadList();
		// Page<Record> page = Db.paginate(1, 20,select,
		// DSqlKit.getSql(),DSqlKit.getParamList());

		List<Record> page = Db.find(select + DSqlKit.getSql(), DSqlKit.getParamList());
		renderJson(page);

	}

	public String loadList() {
		String select = "SELECT *";
		DSqlKit.init(" from sys_menu where 1=1 ");

		String nodeid = getPara("nodeid");

		if (StrKit.isBlank(nodeid)) {
			nodeid = "0";
		}

		if (StrKit.notBlank(nodeid)) {
			DSqlKit.append("and pid = ?", nodeid);
		}
		return select;
	}

	public void tableTree() {
		String sql = "SELECT * FROM SYS_MENU WHERE PID = ?";
		List<Record> pList = Db.use("main").find(sql, "0");
		for (Record r : pList) {
			r.set("text", r.getStr("menuName"));
			List<Record> cList = Db.use("main").find(sql, r.getStr("id"));
			if (cList != null) {
				List<Record> nodeList = new ArrayList<Record>();
				for (Record c : cList) {
					c.set("text", c.getStr("menuName"));
					nodeList.add(c);
				}
				r.set("nodes", nodeList);
			}
		}
		renderJson(pList);
	}
}
