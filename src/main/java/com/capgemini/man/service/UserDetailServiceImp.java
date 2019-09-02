package com.capgemini.man.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.capgemini.man.entity.MyUserDetails;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.entity.UserRole;

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
			List<UserRole> roleList = userRoleService.getRoleListByUserId(Long.valueOf(userId));
			if(roleList.isEmpty()) {
				throw new AuthenticationCredentialsNotFoundException("豁哗，一给窝里");
			}
			return new MyUserDetails(admin, roleList);//自定义的UserDetailService

		}
		throw new UsernameNotFoundException("用户名或密码错误");
	};
}
