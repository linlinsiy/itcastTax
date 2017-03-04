package cn.itcast.home.action;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	//跳转到系统首页
	public String execute(){
		return "home";
	}
}
