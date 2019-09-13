package com.capgemini.man.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.man.entity.RoleInfo;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.service.UserInfoService;
import com.capgemini.man.service.UserRoleService;

@RestController
@RequestMapping("/user")
public class UserInfoContoller {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@GetMapping("/getall")
	public List<UserInfo> selectAll(){
		return userInfoService.selectAll();
	}
	
	@GetMapping("/getall1")
	public List<UserInfo> selectAll1(){
		return userInfoService.selectAll1();
	}
	
	@GetMapping("/getidname")
	public List<UserInfo> getIdName(){
		return userInfoService.getIdName();
	}
	
	@GetMapping("/kankanquanxian")
	public List<RoleInfo> getRoleById(@RequestParam Long userId){
		return userRoleService.getRoleListByUserId(userId);
	}
	
	@PostMapping("/add")
	public int addUserInfoWithLock(@RequestBody UserInfo user) {
		return userInfoService.addUserInfoWithLock(user);
	}
	
	@PostMapping("/update")
	public int minusUserScore(@RequestParam UserInfo user) {
		return userInfoService.update(user);
	}
	
	@PostMapping("/minusScore")
	public int minusUserScore(@RequestParam String userId, @RequestParam Integer score) {
		return userInfoService.minusUserScore(userId, score);
	}
}
