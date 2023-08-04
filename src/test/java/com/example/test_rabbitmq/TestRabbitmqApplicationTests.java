package com.example.test_rabbitmq;

import com.example.test_rabbitmq.utils.RabbitMqUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class TestRabbitmqApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testSendDelayMessage() throws Exception {
		Map<String, Object> map = new HashMap<>(2);
		map.put("orderId", System.currentTimeMillis());
		// 发送消息 延迟2秒
		RabbitMqUtils.sendDelayMessage(rabbitTemplate, 2000, objectMapper.writeValueAsString(map), System.currentTimeMillis());
		log.info("======>>>>>>发送延迟队列消息成功！消息内容：{}", map);
	}
}
