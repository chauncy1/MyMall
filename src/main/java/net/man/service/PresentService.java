package net.man.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import net.man.DO.PresentInfo;
import net.man.mapper.PresentMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
public class PresentService {
	@Autowired
	PresentMapping presentMapping;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisLockRegistry redisLockRegistry;

	private static Semaphore semaphore = new Semaphore(1);

	public int minusPresentCount(String id, Integer count) throws InterruptedException {
		//注意并发
		PresentInfo judge = selectById(id);
		if (judge.getPresentCount() - count < 0) {
			return 0;
		}
		PresentInfo pres = new PresentInfo();
		pres.setPresentId(Long.valueOf(id));
		pres.setPresentCount(count);
		return presentMapping.minusPresentCount(pres);

	}

	public int minusPresentCountBySemaphore(String id, Integer count) throws InterruptedException {
		// 信号量大法
		semaphore.acquire();
		try {
			PresentInfo judge = selectById(id);
			if (judge.getPresentCount() - count < 0) {
				return 0;
			}

			PresentInfo pres = new PresentInfo();
			pres.setPresentId(Long.valueOf(id));
			pres.setPresentCount(count);
			return presentMapping.minusPresentCount(pres);
		} finally {
			semaphore.release();
		}

	}

	public int minusPresentCountByRedis(String id, Integer count) throws InterruptedException {
		PresentInfo pres = new PresentInfo();
		pres.setPresentId(Long.valueOf(id));
		pres.setPresentCount(count);
		//redis分布式锁 有错
		if (stringRedisTemplate.opsForValue().setIfAbsent(id, id, 5L, TimeUnit.MINUTES)) {
			int result = 0;
			try {
				PresentInfo judge = selectById(id);
				if (judge.getPresentCount() - count < 0) {
					return 0;
				}
				result = presentMapping.minusPresentCount(pres);
			} finally {
				stringRedisTemplate.delete(id);
			}
			return result;
		}

		RamdomSleep();
		return minusPresentCount(id, count);

	}

	public int minusPresentCountByRedisLock(String id, Integer count) throws InterruptedException {
		PresentInfo pres = new PresentInfo();
		pres.setPresentId(Long.valueOf(id));
		pres.setPresentCount(count);

		//redis integretion分布式锁
		Lock lock = redisLockRegistry.obtain("minus-present");
		if (lock.tryLock(60, TimeUnit.SECONDS)){
			try {
				PresentInfo judge = selectById(id);
				if (judge.getPresentCount() - count < 0) {
					return 0;
				}
				return presentMapping.minusPresentCount(pres);
			} finally {
				lock.unlock();
			}
		}
		RamdomSleep();
		return minusPresentCount(id, count);
	}

	public void RamdomSleep() {
		try {
			Thread.sleep(new Random().nextInt(10) + 1);
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
