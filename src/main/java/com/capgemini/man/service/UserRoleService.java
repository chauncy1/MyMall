package com.capgemini.man.service;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.man.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.man.mapper.UserRoleMapper;

@Service
public class UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	public List<RoleInfo> getRoleListByUserId(Long userId){
		List<RoleInfo> s = userRoleMapper.getRoleListByUserId(userId);
		return s;
	}
		
}
