package com.capgemini.man.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.capgemini.man.entity.EsProductInfo;

public interface EsProductMapper extends BaseMapper<EsProductInfo>{
	List<EsProductInfo> selectAllEsProductInfo();
}
