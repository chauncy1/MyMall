package net.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.man.entity.RoleInfo;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<RoleInfo>{

	List<RoleInfo> getRoleListByUserId(Long userId);
}
