package com.capgemini.man.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.entity.UserRole;
import com.capgemini.man.service.UserInfoService;
import com.capgemini.man.service.UserRoleService;

@RestController
@RequestMapping("/userinfo")
public class UserInfoContoller {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@GetMapping("/getall")
	public List<UserInfo> selectAll(){
		return userInfoService.selectAll();
	}
	
	@GetMapping("/getidname")
	public List<UserInfo> getIdName(){
		return userInfoService.getIdName();
	}
	
	@GetMapping("/kankanquanxian")
	public List<UserRole> getRoleById(@RequestParam Long userId){
		return userRoleService.getRoleListByUserId(userId);
	}
}
