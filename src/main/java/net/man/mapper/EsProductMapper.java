package net.man.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.man.dto.EsProductDTO;

import java.util.List;

public interface EsProductMapper extends BaseMapper<EsProductDTO>{
	List<EsProductDTO> selectAllEsProductInfo();
}
