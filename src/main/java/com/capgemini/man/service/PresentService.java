package com.capgemini.man.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.capgemini.man.entity.PresentInfo;
import com.capgemini.man.mapper.PresentMapping;

@Service
public class PresentService {
	@Autowired
	PresentMapping presentMapping;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	public int minusPresentCount(String id, Integer count) {
		//在java中判断>0没用 还是会出现负数
//		PresentInfo judge = selectById(id);
//		if(judge.getPresentCount() - count < 0) {
//			return 0;
//		}
		PresentInfo pres = new PresentInfo();
		pres.setPresentId(Long.valueOf(id));
		pres.setPresentCount(count);
		int result = presentMapping.minusPresentCount(pres);
		return result;
		
		//redis分布式锁  慢的一批
//		if(stringRedisTemplate.opsForValue().setIfAbsent(id, id, 5, TimeUnit.MINUTES)) {
//			int result = presentMapping.minusPresentCount(pres);
//			stringRedisTemplate.delete(id);
//			return result;
//		}
//		RamdomSleep();
//		return minusPresentCount(id, count);
		
		
	}
	
	public void RamdomSleep() {
		try {
			Thread.sleep(new Random().nextInt(10)+1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(PresentInfo p) {
		return presentMapping.insert(p);
	}
	
	public PresentInfo selectById(String id) {
		return presentMapping.selectOne(new QueryWrapper<PresentInfo>()
				.eq("present_id", id));
	}
	
	public int update(PresentInfo p) {
		return presentMapping.update(p, new UpdateWrapper<PresentInfo>()
				.eq("present_id", p.getPresentId()));
	}
	
}
