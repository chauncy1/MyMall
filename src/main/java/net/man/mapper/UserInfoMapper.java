package net.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.man.DO.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo>{
	List<UserInfo> getIdName();
	
	int minusUserScore(UserInfo user);
}
