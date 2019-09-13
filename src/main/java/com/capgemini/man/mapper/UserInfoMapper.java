package com.capgemini.man.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.capgemini.man.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo>{
	List<UserInfo> getIdName();
	
	int minusUserScore(UserInfo user);
}
