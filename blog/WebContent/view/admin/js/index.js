/**
 * @author willcen
 *  
 */

$(function() {
	_init();
	ClickMenu();
})

var _admin = $(".admin");
_init = function() {
	var _admin = $(".admin");
	$(".cc-nav-a").each(function(index) {
		let _this = $(this);
		_this.on("click", function() {
			var _fp = _this.attr("fp");
			_this.parent().parent().remove();
			var _am = $("#" + _fp);
			_am.prev().addClass("active"); //前一个元素
			_am.remove();
		})
	});
	//切换tab页签内容
	$(".cc-nav-btn").each(function(index) {
		let _this = $(this);
		_this.on("click", function() {
			_admin.removeClass("active");
			_admin.eq(index).addClass("active");
		})
	});
}

function ClickMenu() {
	$(".sideMenu ul li").on("click", function() {
		let _ccNav = $(".daohang ul");
		let _adminContent = $(".admin-content");
		let _this = $(this);
		
		let _url = _this.attr("url");
		
		let _title = _this.text();
		let _id = "cc-" + Date.now();
		var _navCon = navLi(_id, _title);
		var _admCon = admCotent(_id, _url);
		_ccNav.append(_navCon);
		_adminContent.append(_admCon);
		_admin.removeClass("active");
		_init(); //重新加载DOM结构
	})
}

function navLi(id, tabName) {
	var s = "<li>\n" +
		"<button type='button' class='am-btn am-btn-default am-radius am-btn-xs cc-nav-btn'>" + tabName +
		"\n<a href='javascript: void(0)' fp='" + id + "' class='am-close am-close-spin cc-nav-a' data-am-modal-close=''>×</a>" +
		"</button>\n</li>"
	return s;
}

function admCotent(id, url) {
	var _iframe = "<iframe scrolling='' width='100%' height='100%' src='" + url + "'></iframe>";
	var s = "<div class='admin active' id='" + id + "'>" + _iframe + "</div>";
	return s;
}

//构建左侧菜单树
_createLeftMenu();
function _createLeftMenu() {
	$.getJSON("/menu/tableTree", function(data) {
		var _treeData = "";
		var noChildNode = "";
		data.forEach(x => {
			var nodeData = "";
			var subData = "";
			if (x.nodes) {
				var len = x.nodes;
				nodeData += "<h3 class='" + x.iconFa + "'><em></em> <a href='javascript:void(0)'>" + x.text + "</a></h3>\n<ul>\n";
				for (let i = 0; i < len.length; i++) {
					subData += "<li url='" + len[i].url + "'>" + len[i].text + "</li>\n";
				}
				subData = subData + "</ul>\n";
			} else {
				noChildNode += "<h3 class='" + x.iconFa + "'><em></em> <a href='javascript:void(0)'>" + x.text + "</a></h3>";
			}
			nodeData += subData;
			_treeData += nodeData;
		});
		_treeData += noChildNode;
		$(".MenuTree").append(_treeData);
	})


}