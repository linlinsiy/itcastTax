package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import cn.itcast.core.dao.util.ExcelUtil;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	
	private UserDao userDao;
	
	@Resource
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}


	@Override
	public void delete(Serializable id) {
		//删除用户对应角色
		userDao.deleteUserRoleByUserId(id);
		//删除用户
		userDao.delete(id);
	}

	@Override
	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		ExcelUtil.exportExcel(userList, outputStream);
	}	

	@Override
	public void importExcel(File userExcel) {
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			Workbook workbook = WorkbookFactory.create(userExcel);
			Sheet sheet = workbook.getSheetAt(0);
			if(sheet.getPhysicalNumberOfRows() > 2){
				User user = null;
				Row row = null;
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					user = new User();
					user.setName(row.getCell(0).getStringCellValue());
					user.setAccount(row.getCell(1).getStringCellValue());
					user.setDept(row.getCell(2).getStringCellValue());
					user.setGender("男".equals(row.getCell(3).getStringCellValue()));
					String mobile;
					try {
						mobile = row.getCell(4).getStringCellValue();
					} catch (Exception e) {
						double dMobile = row.getCell(4).getNumericCellValue();
						mobile = BigDecimal.valueOf(dMobile).toString();
					}
					user.setEmail(row.getCell(5).getStringCellValue());
					if(row.getCell(6).getDateCellValue() != null){
						user.setBirthday(row.getCell(6).getDateCellValue());
					}
					user.setState(User.USER_STATE_VALID);
					user.setPassword("123456");
					save(user);
				}
			}
			workbook.close();
			fileInputStream.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<User> findUsersByAccountAndId(String account, String id) {
		return userDao.findUsersByAccountAndId(account,id);
	}

	@Override
	public void saveUserAndRole(User user, String... roleIds) {
		//保存用户
		save(user);//保存过后同步线程红的用户id
		//保存角色id
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(user.getId(),new Role(roleId))));
				//新建的UserRoleId只会存入roleid所以新建一个Role给一个Id即可，不需要查询数据库
			}
		}
	}

	@Override
	public void updateUserAndRole(User user, String... roleIds) {
		//删除该用户对应的所有角色
		userDao.deleteUserRoleByUserId(user.getId());//update方法，所以user有id
		//更新用户
		update(user);
		//更新角色id
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(user.getId(),new Role(roleId))));
				//新建的UserRoleId只会存入roleid所以新建一个Role给一个Id即可，不需要查询数据库
			}
		}
	}

	@Override
	public List<UserRole> findUserRolesByUserId(String id) {
		return userDao.findUserRolesByUserId(id);
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account, String password) {
		List<User> list = userDao.findUsersByAccountAndPass(account, password);
		return list;
	}
	
	

}
