package com.capgemini.man.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.man.service.PresentService;

@RestController
@RequestMapping("/present")
public class PresentController {

	@Autowired
	PresentService presentService;
	
	@PostMapping("/minusCount")
	public int minusPresentCount(@RequestParam String id,@RequestParam Integer count) {
		return presentService.minusPresentCount(id, count);
	}
}
