package com.capgemini.man.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.capgemini.man.entity.EsProductInfo;


public interface EsProductRepository extends ElasticsearchRepository<EsProductInfo,Long>{

	Page<EsProductInfo> findByKeywords(String keywords, Pageable pageable);
}
