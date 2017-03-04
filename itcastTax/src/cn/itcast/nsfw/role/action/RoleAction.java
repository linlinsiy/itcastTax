package cn.itcast.nsfw.role.action;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.constant.Constant;
import cn.itcast.core.exception.ActionException;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.role.entity.RolePrivilegeId;
import cn.itcast.nsfw.role.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RoleAction extends BaseAction {
	
	@Resource
	private RoleService roleService;
	
	private Role role;
	private String[] privilegeIds;
	private String strName;
	
	//列表
	public String listUI() throws ActionException{
		//加载系统的权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVATE_MAP);
		try {
			QueryHelper helper = new QueryHelper(Role.class,"r");//2
			if(role != null){
				if(StringUtils.isNotBlank(role.getName())){
					role.setName(URLDecoder.decode(role.getName(), "utf-8"));
					helper.addCondition("r.name like ?", "%" +role.getName() + "%");
				}
			}
			pageResult = roleService.getPageResult(helper,getPageNo(),getPageSize());//2
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载系统的权限集合
		Map<String, Object> map = ActionContext.getContext().getContextMap();
		map.put("privilegeMap", Constant.PRIVATE_MAP);
		strName = role.getName();
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			//保存用户
			if(role != null){
				//处理角色权限
				if(privilegeIds != null){
					Set<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(String code : privilegeIds){
						set.add(new RolePrivilege(new RolePrivilegeId(role,code)));
					}
					role.setRolePrivileges(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载系统的权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVATE_MAP);
		if(role != null && StringUtils.isNotBlank(role.getRoleId())){
			strName = role.getName();
			role = roleService.findObjectById(role.getRoleId());
			//处理角色权限的回显
			if(role.getRolePrivileges() != null && role.getRolePrivileges().size() > 0){
				privilegeIds = new String[role.getRolePrivileges().size()];
				int i=0;
				for (RolePrivilege rp : role.getRolePrivileges()) {
					privilegeIds[i++] = rp.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(role != null){
				//处理角色权限
				if(privilegeIds != null){
					Set<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(String code : privilegeIds){
						set.add(new RolePrivilege(new RolePrivilegeId(role,code)));
					}
					role.setRolePrivileges(set);
				}
				roleService.update(role);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//根据id删除
	public String delete(){
		if(role != null && StringUtils.isNotBlank(role.getRoleId())){
			strName = role.getName();
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null){
			strName = role.getName();
			for(String id: selectedRow){
				roleService.delete(id);
			}
		}
		return "list";
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	

}
