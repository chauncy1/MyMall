package com.capgemini.man.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.man.common.result.CommonResult;
import com.capgemini.man.entity.EsProductInfo;
import com.capgemini.man.service.ESProductService;

@RestController
@RequestMapping("/es")
public class ESProductController {

	@Autowired
	ESProductService esProductService;
	
	@GetMapping("/saveAll")
	public CommonResult<Integer> saveAll(){
		int count = esProductService.saveAll();
		return CommonResult.success(count);
	}
	
	@GetMapping("/getAll")
	public CommonResult<Page<EsProductInfo>> getAll(
			@RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "15") int pageSize){
		Page<EsProductInfo> result = esProductService.getAll(pageNum, pageSize);
		return CommonResult.success(result);
	}
	
	@GetMapping("/search/keywords")
	public CommonResult<Page<EsProductInfo>> findByKeywords(
			@RequestParam String keywords,
			@RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "15") int pageSize){
		Page<EsProductInfo> prodPage = esProductService.searchByKeyword(keywords, pageNum, pageSize);
		return CommonResult.success(prodPage);
	}
}
