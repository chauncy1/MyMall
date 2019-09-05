package com.capgemini.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.capgemini.man.entity.RoleInfo;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<RoleInfo>{

	List<RoleInfo> getRoleListByUserId(Long userId);
}
