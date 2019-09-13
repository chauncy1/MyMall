package com.capgemini.man.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.mapper.UserInfoMapper;

@Service
public class UserInfoService{
	@Autowired
	UserInfoMapper userInfoMapper;
	
	@Autowired
	RedisTemplate<String, ?> redisTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 使用存入redis的方式加快查询，使用了JSON，快了吗？
	 * @return
	 */
	public List<UserInfo> selectAll(){
		List<Object> list = redisTemplate.boundHashOps("all").values();
		//accept json to convert from object list to entity list
		String jsonString = JSONObject.toJSONString(list);
		List<UserInfo> userList = (List<UserInfo>)JSONObject.parse(jsonString);
		if(list.isEmpty()) {
			userList = userInfoMapper.selectList(null);
			for(UserInfo user: userList) {
				redisTemplate.boundHashOps("all").putIfAbsent(user.getUserId(), user);
				redisTemplate.boundHashOps("all").expire(60, TimeUnit.MINUTES);
			}
		}
		return userList;
	}
	
	public List<UserInfo> selectAll1(){
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

	public int addUserInfoWithLock(UserInfo user) {
		int result = userInfoMapper.insert(user);
		return result;
	}
	
	public int update(UserInfo user) {
		return userInfoMapper.updateById(user);
	}

	public int minusUserScore(String id, Integer score) {
		//上锁
		Boolean x = stringRedisTemplate.opsForValue().setIfAbsent(id, id, 5, TimeUnit.MINUTES);
		if(x == false) {
			return minusUserScore(id,score);
		}
		
		//减分
		UserInfo user = new UserInfo();
		user.setUserId(Long.valueOf(id));
		user.setUserScore(score);
		int result = userInfoMapper.minusUserScore(user);
		
		//解锁
		stringRedisTemplate.delete(id);
		return result;
	}
 }
