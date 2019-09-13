package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.man.PlusApplication;
import com.capgemini.man.entity.PresentInfo;
import com.capgemini.man.service.PresentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LockApplicationTests {
	@Autowired
	PresentService presentService;
	
	private static final int USER_NUM = 2000;//总人数
	private static final int P_COUNT = 1000;//商品总数
	
	private static int successPreson = 0;
	
	private static int saleNum = 0;
	
	private static CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);
	
	@Before
	public void before() {
		PresentInfo p = new PresentInfo();
		p.setPresentId(5L);
		p.setPresentCount(P_COUNT);
		presentService.update(p);
	}
	
	@Test
	public void testMinusPresentCount() throws InterruptedException {
		for(int i = 0;i < USER_NUM;i++) {
			//买id为5的商品，每人买3个
			new Thread(new minusRequest("5",3)).start();
			countDownLatch.countDown();
		}
		
		Thread.currentThread();
		Thread.sleep(10000);
		log.info("成功购买的人数： " + successPreson);
		log.info("卖出的商品个数： " + saleNum);
		log.info("剩余商品个数： "+ presentService.selectById("5").getPresentCount());
		
	}
	
	@AllArgsConstructor
	public class minusRequest implements Runnable{
		private String id;
		private Integer count;
		
		@Override
		public void run() {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int updateResult = presentService.minusPresentCount(id, count);
			
			
			//计数
			if(updateResult > 0) {
				synchronized(countDownLatch) {
					successPreson++;
					saleNum += count;
				}
			}
		}
		
	}

}
