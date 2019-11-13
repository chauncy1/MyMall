package com.capgemini.man.controller;

import com.capgemini.man.entity.RoleInfo;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.service.UserInfoService;
import com.capgemini.man.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoContoller {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@GetMapping("/getall")
	public List<UserInfo> selectAll(){
		return userInfoService.selectAllWithRedis();
	}
	
	@GetMapping("/getall1")
	public List<UserInfo> selectAll1(){
		return userInfoService.selectAll();
	}

	@GetMapping("/getidname")
	public List<UserInfo> getIdName(){
		return userInfoService.getIdName();
	}

	@PreAuthorize("hasAuthority('caiji')")
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
