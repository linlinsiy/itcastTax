package cn.itcast.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.core.permission.impl.PermissionCheckImpl;
import cn.itcast.nsfw.user.entity.User;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//判断是否是登录请求
		String uri = request.getRequestURI();
		if(!uri.contains("/sys/login_")){//非登录请求
			//判断session中是否有用户信息
			if(request.getSession().getAttribute(Constant.USER) != null){//已登录
				//判断是否访问纳税服务系统
				if(uri.contains("/nsfw/")){//访问纳税服务子系统，需要权限判断
					User user = (User) request.getSession().getAttribute(Constant.USER);
					//WebApplicationContext获取的是随着应用服务器启动时实例化的ioc容器
					WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc = (PermissionCheck) context.getBean("permissionCheck");
					if(pc.isAccessble(user, "nsfw")){//有权限
						chain.doFilter(request, response);
					}else {//无权限
						response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
					}
				}else{//非纳税服务子系统
					chain.doFilter(request, response);					
				}
			}else{//未登录
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");				
			}
		}else{//登录请求
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
