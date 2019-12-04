package net.man.service;

import net.man.DTO.MyUserDetails;
import net.man.entity.RoleInfo;
import net.man.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component	
public class UserDetailServiceImp implements UserDetailsService {
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserRoleService userRoleService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// 获取登录用户信息
		UserInfo admin = userInfoService.selectById(userId);
		if (admin != null) {
			List<RoleInfo> roleList = userRoleService.getRoleListByUserId(Long.valueOf(userId));
			return new MyUserDetails(admin, roleList);//自定义的UserDetailService
		}
		throw new UsernameNotFoundException("用户名或密码错误");
	}
}
