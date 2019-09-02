package com.capgemini.man.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.mapper.UserInfoMapper;

@Service
public class UserInfoService{
	@Autowired
	UserInfoMapper userInfoMapper;
	
	public List<UserInfo> selectAll(){
		return userInfoMapper.selectList(null);
	}
	
	public List<UserInfo> getIdName(){
		return userInfoMapper.getIdName();
	}
	
	public UserInfo selectByUserName(String userName) {
		QueryWrapper<UserInfo> ew = new QueryWrapper<UserInfo>();
		ew.eq("user_name", userName);
		return userInfoMapper.selectOne(ew);
	}
	
	public UserInfo selectByUserAccount(String userAccount) {
		QueryWrapper<UserInfo> ew = new QueryWrapper<UserInfo>();
		ew.eq("user_account", userAccount);
		return userInfoMapper.selectOne(ew);
	}
	
	public UserInfo selectById(String userId) {
		QueryWrapper<UserInfo> ew = new QueryWrapper<UserInfo>();
		ew.eq("user_id", userId);
		return userInfoMapper.selectOne(ew);
	}
 }
