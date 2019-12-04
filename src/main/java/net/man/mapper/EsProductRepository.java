package net.man.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.man.DTO.EsProductDTO;


public interface EsProductRepository extends ElasticsearchRepository<EsProductDTO,Long>{

	Page<EsProductDTO> findByKeywords(String keywords, Pageable pageable);
}
