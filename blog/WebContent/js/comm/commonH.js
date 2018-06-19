__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var path = __CreateJSPath("commonH.js");
path = path.replace("/js/comm/", "");
window.g_path = path;

document.write('<script src="' + path + '/view/admin/plugins/layui/layui.js" type="text/javascript" ></sc' + 'ript>');
document.write('<script src="' + path + '/view/admin/build/js/app.js" type="text/javascript" ></sc' + 'ript>');
document.write('<script src="' + path + '/view/admin/build/css/nprogress.css" type="text/javascript" ></sc' + 'ript>');
//document.write('<script src="' + path + 'view/adminRes/layui/lay/modules/table.js" type="text/javascript" ></sc' + 'ript>');

document.write('<link href="' + path + '/view/admin/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + path + '/view/admin/plugins/layui/css/layui.css" rel="stylesheet" type="text/css" />');
/**
 * 异步ajax请求
 * @param url，必填
 * @param arg
 * @param isShowLoading
 * @param callback
 */
function getJson(url, arg, isShowLoading, callback){
    getJsonFul(url, arg, isShowLoading, callback,true);
}
function getJsonFul(url, arg, isShowLoading, callback,isSyn) {
    if (typeof (arg) === "function") {
        callback = arg;
        isShowLoading = false;
        arg = null;
    }
    
    if (typeof (isShowLoading) === "function") {
        callback = isShowLoading;
        isShowLoading = false;
    }
    
    var paramData = _convertAjaxArg(arg);
    var param = {
        type: "POST",
        url: url,
        async: isSyn,
        data: paramData,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
        	if(callback)
        		callback(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus && textStatus == "timeout") {
                MsgBox.alert("网络超时，请重试！");
            }
            else if (textStatus && textStatus == "error") {
                MsgBox.alert("系统异常，请重试！");
            }
        },
        complete: function () {
            if (isShowLoading)
                hideLoading();
        }
    };

    if (isShowLoading)
        showLoading();
    $.ajax(param);
}

function _convertAjaxArg(arg){
	if ( arg && typeof arg === "object" ){
		var s = [];
		var s2 = [];
		for ( var key in arg ){
			s.push( encodeURIComponent(key) + "=" + lpAjaxEncode( jQuery.isFunction(arg[key]) ? arg[key]() : arg[key] ) );
			s2.push(key);
		}
		if(window.LpAjaxEncode && s.length > 0)
			s.push("_lpkeys=" + lpAjaxEncode(s2.join("&")));
		
		return s.join("&").replace(/%20/g, "+");
	}		
				
	return arg;
}

window.loadingcount = 0;
function showLoading() {
    window.loadingcount++;
    MsgBox.loading();
}

function hideLoading() {
    window.loadingcount--;
    if (window.loadingcount < 1) {
        window.loadingcount = 0;
        MsgBox.unloading();
    }
}

var MsgBox = {
    alert: function (info, callback) {
    	var mini = window.mini;
    	if(window.top && window.top.mini){
    		mini = window.top.mini;
    	}
    	
    	mini.MessageBox.show({
            minWidth: 250,
            title: "系统提示",
            buttons: ["ok"],
            message: info,
            iconCls: "mini-messagebox-info",
            callback: callback
        });
    },
    warn: function (info, callback) {
    	var mini = window.mini;
    	if(window.top && window.top.mini){
    		mini = window.top.mini;
    	}
    	
    	mini.MessageBox.show({
            minWidth: 250,
            title: "系统提示",
            buttons: ["ok"],
            message: info,
            iconCls: "mini-messagebox-warning",
            callback: callback
        });
    },
    confirm: function (info, callback) {
    	var mini = window.mini;
    	if(window.top && window.top.mini){
    		mini = window.top.mini;
    	}
    	
    	mini.MessageBox.show({
            minWidth: 250,
            title: "系统确认",
            buttons: ["ok", "cancel"],
            message: info,
            iconCls: "mini-messagebox-question",
            callback: function(action){
            	var re = action == "ok" ? true : false;
    			if (callback)
    	            callback(re);
            }
        });
    },
    loading : function(){
    	var mini = window.mini;
    	var doc = window.document;
    	if(window.top && window.top.mini){
    		mini = window.top.mini;
    		doc = window.top.document;
    	}
    	mini.mask({
            el: doc.body,
            cls: 'mini-mask-loading',
            html: '加载中...'
        });
    },
    unloading : function(){
    	var mini = window.mini;
    	var doc = window.document;
    	if(window.top && window.top.mini){
    		mini = window.top.mini;
    		doc = window.top.document;
    	}
    	mini.unmask(doc.body);
    },
    oping:function(){
    	var mini = window.mini;
    	return mini.loading("数据加载...", "处理中...");
    },
    unoping:function(messageid){
    	 mini.hideMessageBox(messageid);
    },
    showTips : function(options){
		var defaultOpt = {
				showModal: true,
	            width: 250,
	            title: "系统提示",
	            iconCls: "mini-messagebox-info",
	            message: "",
	            timeout: 3000,
	            x: "center",
	            y: 200
		}
		
		if(jQuery.isPlainObject(options)){
			jQuery.extend(defaultOpt,options);
		}
		else{
			if(arguments.length > 0){
				defaultOpt.message = arguments[0];
			}
			if(arguments.length > 1){
				defaultOpt.iconCls = arguments[1];
			}
			if(arguments.length > 2){
				defaultOpt.x = arguments[2];
			}
			if(arguments.length > 3){
				defaultOpt.y = arguments[3];
			}
			if(arguments.length > 4){
				defaultOpt.timeout = arguments[4];
			}
		}
		window.mini.showMessageBox(defaultOpt);
	},
	showTips2 : function(options){
		var defaultOpt = {
			    content: "",    
			    state: "info",      //default|success|info|warning|danger
			    x: "center",          //left|center|right
			    y: "top",          //top|center|bottom
			    timeout: 3000     //自动消失间隔时间。
			}
			
			if(jQuery.isPlainObject(options)){
				jQuery.extend(defaultOpt,options);
			}
			else{
				if(arguments.length > 0){
					defaultOpt.content = arguments[0];
				}
				if(arguments.length > 1){
					defaultOpt.state = arguments[1];
				}
				if(arguments.length > 2){
					defaultOpt.x = arguments[2];
				}
				if(arguments.length > 3){
					defaultOpt.y = arguments[3];
				}
				if(arguments.length > 4){
					defaultOpt.timeout = arguments[4];
				}
			}
			window.top.mini.showTips(defaultOpt);
		},
	openWin : function(url,title,w,h,func1,func2) {
            mini.open({
            url: url,
            title: title, width: w, height: h,
            onload: function () {
                var iframe = this.getIFrameEl();
                if(func1)
                    func1(iframe.contentWindow);
            },
            ondestroy: function (action) {
                var iframe = this.getIFrameEl();
                if (func2)
                    func2(action,iframe.contentWindow);
            }
        });
    },
    treeTips:function(){
    	var content = "该功能不支持此节点" ;
    	this.showTips(content,"mini-messagebox-info","center",200,1000);
    }
    ,
	showInfoTips : function(info){
		var content =  info;
		this.showTips(content,"mini-messagebox-info");
	},
	showSuccessTips : function(info){
		var content =  info;
		this.showTips(content,"mini-messagebox-info");
	},
	showWarningTips : function(info){
		var content =  info;
		this.showTips(content,"mini-messagebox-warning");
	},
	showErrTips : function(info){
		var content =  info;
		this.showTips(content,"mini-messagebox-error");
	},
	showInfoTips2 : function(info){
		var content = "<b>提示</b> <br/>" + info;
		this.showTips2(content,"info");
	}
    
}
