package com.capgemini.man.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capgemini.man.entity.EsProductInfo;
import com.capgemini.man.mapper.EsProductMapper;
import com.capgemini.man.mapper.EsProductRepository;

@Service
public class ESProductService {

	@Autowired
	EsProductMapper esProductMapper;
	
	@Autowired
	EsProductRepository esProductRepository;
	
	public int saveAll() {
		List<EsProductInfo> productList = esProductMapper.selectAllEsProductInfo();
		Iterable<EsProductInfo> esProductIterable = esProductRepository.saveAll(productList);
		Iterator<EsProductInfo> iterator = esProductIterable.iterator();
		int count = 0;
		while(iterator.hasNext()) {
			count++;
			iterator.next();
		}
		return count;
	}

	public Page<EsProductInfo> getAll(int pageNum, int pageSize) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<EsProductInfo> prodPage = esProductRepository.findAll(pageable);
		return prodPage;
	}
	
	public Page<EsProductInfo> searchByKeyword(String keywords, int pageNum, int pageSize) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		return esProductRepository.findByKeywords(keywords, pageable);
	}

	
}
