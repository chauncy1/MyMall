package net.man.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueEnum {
	/**
	 * 消息通知队列
	 */
	QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
	/**
	 * 消息通知延时队列
	 */
	QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl"),
	/**
	 * 普通消息队列
	 */
	QUEUE_NORMAL("mall.normal.direct", "mall.normal.queue", "mall.normal.queue");
	/**
	 * 交换名称
	 */
	private String exchange;
	/**
	 * 队列名称
	 */
	private String name;
	/**
	 * 路由键
	 */
	private String routeKey;

}