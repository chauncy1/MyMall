package net.man.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.man.common.enumerate.OrderStatusEnum;
import net.man.common.result.CommonResult;
import net.man.component.mq.CancelOrderSender;
import net.man.controller.request.OrderRequest;
import net.man.entity.OrderInfo;
import net.man.entity.PresentInfo;
import net.man.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

	@Autowired
	CancelOrderSender cancelOrderSender;

	@Autowired
	OrderInfoMapper orderInfoMapper;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	PresentService presentService;

	@Transactional
	public CommonResult generateOrder(OrderRequest orderRequest) {
		log.info("process generateOrder");

		//减礼品数量
		int pResult = presentService.minusPresentCountByRedis(String.valueOf(orderRequest.getPresentId()), orderRequest.getPresentCount());
		if (pResult < 0) {
			return CommonResult.failed("库存不足");
		}

		//生成订单信息
		PresentInfo presentInfo = presentService.selectById(String.valueOf(orderRequest.getPresentId()));
		String orderId = String.valueOf(UUID.randomUUID());
		OrderInfo orderInfo = new OrderInfo(orderId,
				orderRequest.getUserId(),
				orderRequest.getPresentId(),
				orderRequest.getPresentCount(),
				presentInfo.getPresentScore() * orderRequest.getPresentCount(),
				OrderStatusEnum.NO_PAID.getStatus());
		orderInfo.setCreateBy(userInfoService.selectById(String.valueOf(orderInfo.getUserId())).getUserName());
		orderInfo.setCreateTime(new Date());
		orderInfoMapper.insert(orderInfo);

		//下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
		sendDelayMessageCancelOrder(orderId);
		return CommonResult.success(orderId, "下单成功");
	}

	@Transactional
	public CommonResult payOrder(String orderId) {
		OrderInfo orderInfo = selectById(orderId);
		if (Objects.isNull(orderInfo)){
			return new CommonResult(404, "订单不存在", null);
		}

		//扣除用户积分
		int uResult = userInfoService.minusUserScoreByRedis(String.valueOf(orderInfo.getUserId()), orderInfo.getPresentScore());
		if (uResult < 0) {
			return CommonResult.failed("用户积分不足");
		}
		orderInfo.setOrderType(OrderStatusEnum.PAID.getStatus());
		orderInfoMapper.updateById(orderInfo);

		return CommonResult.success(null, "付款成功");
	}

	@Transactional
	public CommonResult cancelOrder(String orderId) {
		log.info("process cancelOrder orderId:{}", orderId);
		OrderInfo orderInfo = selectById(orderId);
		if (Objects.isNull(orderInfo)){
			return new CommonResult(404, "订单不存在", null);
		}

		//加礼品数量
		PresentInfo presentInfo = presentService.selectById(String.valueOf(orderInfo.getPresentId()));
		presentInfo.setPresentCount(presentInfo.getPresentCount() + orderInfo.getPresentCount());
		presentService.update(presentInfo);

		//删除订单信息
		deleteById(orderId);

		return CommonResult.success(null, "取消订单成功");
	}

	private void sendDelayMessageCancelOrder(String orderId) {
		//获取订单超时时间，假设为30分钟
		long delayTimes = 300 * 1000;
		//发送延迟消息
		cancelOrderSender.sendMessage(orderId, delayTimes);
	}

	public OrderInfo selectById(String orderId) {
		QueryWrapper<OrderInfo> qw = new QueryWrapper<>();
		qw.eq("id", orderId);
		return orderInfoMapper.selectOne(qw);
	}

	public int deleteById(String orderId){
		QueryWrapper<OrderInfo> qw = new QueryWrapper<>();
		qw.eq("id", orderId);
		return orderInfoMapper.delete(qw);
	}
}
