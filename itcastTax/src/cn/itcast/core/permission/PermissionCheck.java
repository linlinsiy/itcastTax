package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;

public interface PermissionCheck {
	/**
	 * 校验user中是否包含有code对应的权限
	 * @param user 用户
	 * @param code 权限对应的值
	 * @return
	 */
	public boolean isAccessble(User user, String code);
}
