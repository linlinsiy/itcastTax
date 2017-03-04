package cn.itcast.nsfw.user.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.exception.ActionException;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction {
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	private User user;
	
	private File headImg;
	private String headImgFileName;
	private String headImgContentType;
	private File userExcel;
	private String userExcelFileName;
	private String userExcelContentType;
	private String[] roleIds;
	private String strName;
	
	//列表
	public String listUI() throws ActionException{
		try {
			QueryHelper helper = new QueryHelper(User.class,"");//2
			if(user != null){
				if(StringUtils.isNotBlank(user.getName())){
					user.setName(URLDecoder.decode(user.getName(), "utf-8"));
					helper.addCondition("name like ?", "%" +user.getName() + "%");
				}
			}
			pageResult = userService.getPageResult(helper,getPageNo(),getPageSize());//2
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载角色列表
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		strName = user.getName();
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			//保存用户
			if(user != null){
				//处理用户头像
				if(headImg != null){
					//1.获取
					//2.保存头像文件
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replace("-", "") + headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath,fileName));
					//3.设置用户头像路径
					user.setHeadImg("user/" + fileName);
				}
				userService.saveUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载角色列表
		List<Role> roles = roleService.findObjects();		
		ActionContext.getContext().getContextMap().put("roleList", roles);
		if(user != null && StringUtils.isNotBlank(user.getId())){
			strName = user.getName();
			user = userService.findObjectById(user.getId());
			//处理角色回显问题
			List<UserRole> list = userService.findUserRolesByUserId(user.getId());
			if(list != null && list.size() > 0 ){
				roleIds = new String[list.size()];
				int i = 0;
				for (UserRole ur : list) {
					roleIds[i++] = ur.getId().getRole().getRoleId();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(user != null){
				//处理用户头像
				if(headImg != null){
					//1.获取
					//2.保存头像文件
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replace("-", "") + headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath,fileName));
//					new File(filePath,user.getHeadImg().substring(user.getHeadImg().lastIndexof("/"))).delete();
					//3.设置用户头像路径
					user.setHeadImg("user/" + fileName);
				}
				userService.updateUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//根据id删除
	public String delete(){
		if(user != null && StringUtils.isNotBlank(user.getId())){
			strName = user.getName();
			userService.delete(user.getId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null){
			strName = user.getName();
			for(String id: selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}
	
	//导出用户列表
	public void exportExcel(){
		try {
			//1、获取用户列表
			//2、输出excel
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userService.findObjects(),outputStream);
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//导入
	public String importExcel(){
		if(userExcel != null){
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				userService.importExcel(userExcel);
			}
		}
		return "list";
	}
	
	//校验账号唯一性
	public void verifyAccount(){
		try {
			//1.获取账号、id
			if(user != null && StringUtils.isNotBlank(user.getAccount())){
				String res = "true";
				//2.根据账号、id查询用户记录
				List<User> userList = userService.findUsersByAccountAndId(user.getAccount(),user.getId());
				if(userList != null && userList.size() > 0){//说明账号已存在
					res="false";
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(res.getBytes());
				outputStream.close();			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public String getUserExcelContentType() {
		return userExcelContentType;
	}
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	

}
