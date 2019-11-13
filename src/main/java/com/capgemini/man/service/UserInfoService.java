package com.capgemini.man.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.mapper.UserInfoMapper;
import com.capgemini.man.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoService {
	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	RedisTemplate<String, ?> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 使用存入redis的方式加快查询
	 *
	 * @return
	 */
	public List<UserInfo> selectAllWithRedis() {
		List<Object> list = redisTemplate.boundHashOps("users").values();
		//accept json to convert from object list to entity list
		String jsonString = JSONObject.toJSONString(list);
		List<UserInfo> userList = JSONArray.parseArray(jsonString, UserInfo.class);
		if (list.isEmpty()) {
			userList = userInfoMapper.selectList(null);
			for (UserInfo user : userList) {
				redisTemplate.boundHashOps("users").putIfAbsent(user.getUserId(), user);
				redisTemplate.boundHashOps("users").expire(5, TimeUnit.MINUTES);
			}
		}
		return userList;
	}
//    public List<UserInfo> selectAllWithRedis(){
//        String listFromJson = redisUtil.get("all_user_list");
//        List<UserInfo> userList = new ArrayList<>();
//        if(StringUtils.isEmpty(listFromJson)) {
//            userList = userInfoMapper.selectList(null);
//            String userListJson = JSONArray.toJSONString(userList);
//            redisUtil.set("all_user_list", userListJson);
//        }else{
//            userList = JSONArray.parseArray(listFromJson, UserInfo.class);
//        }
//        return userList;
//    }

	public List<UserInfo> selectAll() {
		return userInfoMapper.selectList(null);
	}

	public List<UserInfo> getIdName() {
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
		if (x == false) {
			return minusUserScore(id, score);
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
