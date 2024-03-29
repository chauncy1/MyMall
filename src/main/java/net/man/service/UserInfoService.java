package net.man.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.man.entity.UserInfo;
import net.man.mapper.UserInfoMapper;
import net.man.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
	 * update和delete操作也要更新redis，否则会有缓存时效性问题
	 *
	 * @return
	 */
	public List<UserInfo> selectAllWithRedis() {
		List<Object> list = redisTemplate.boundHashOps("users").values();
		//accept json to convert from object list to DO list
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
		QueryWrapper<UserInfo> ew = new QueryWrapper<>();
		ew.eq("user_account", userAccount);
		return userInfoMapper.selectOne(ew);
	}

	public UserInfo selectById(String userId) {
		QueryWrapper<UserInfo> ew = new QueryWrapper<>();
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

	public int minusUserScoreByRedis(String userId, Integer score) {
		int result = 0;
		UserInfo user = this.selectById(userId);
		user.setUserScore(score);

		if (user.getUserScore() - score < 0) {
			return -1;
		}
		result = userInfoMapper.minusUserScore(user);
		return result;
	}
}
