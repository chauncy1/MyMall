package net.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.man.entity.PresentInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentMapping extends BaseMapper<PresentInfo>{

	int minusPresentCount(PresentInfo pres);
	

}
