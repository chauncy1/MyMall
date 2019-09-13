package com.capgemini.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.capgemini.man.entity.PresentInfo;

public interface PresentMapping extends BaseMapper<PresentInfo>{

	int minusPresentCount(PresentInfo pres);
	

}
