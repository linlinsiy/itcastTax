package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private User user;
	private String loginResult;
	
	
	@Resource
	private UserService userService;
		
	//跳转到登录页面
	public String toLoginUI(){
		return "loginUI";
	}
	//登录
	public String login(){
		Log log = LogFactory.getLog(getClass());
		if(user != null){
			//1.1、根据账号、密码查询用户记录
			if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
				//1.2、根据账号、密码查询用户记录
				List<User> list = userService.findUsersByAccountAndPass(user.getAccount(), user.getPassword());
				if(list != null && list.size() > 0){
					//1.3、登录成功（有用户记录）
					//1.3.1、获取用户信息
					user = list.get(0);
					//1.3.2、根据用户id将用户对应的所有用户角色设置到用户中
					user.setUserRoles(userService.findUserRolesByUserId(user.getId()));
					//1.3.3、将用户信息存入session
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//1.3.4、将用户登录信息记录到日志文件
					log.info("用户名称为：" + user.getName() + "的用户登录系统。");
					//1.3.5、跳转到系统首页（重定向）
					return "home";
				}else {
					//1.4、登录失败
					log.info("账号或密码错误");
					loginResult = "账号或密码错误";
				}				
			}else{
				log.info("账号或密码不能为空");
				loginResult = "账号或密码不能为空";
			}
		}else{
			log.info("账号密码不能为空");
			loginResult = "账号密码不能为空";
		}
		//1.1 
		return toLoginUI();
	}
	//注销、退出
	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	//跳转到没有权限提示页面
	public String toNoPermissionUI(){
		return "noPermissionUI";
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
}
