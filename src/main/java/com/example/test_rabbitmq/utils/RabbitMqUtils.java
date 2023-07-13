package com.example.test_rabbitmq.utils;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author ：liujl
 * @date ：Created 2023/7/12 17:37
 * @description：
 */
public class RabbitMqUtils {

    /**
     * 发送延迟消息
     * @param rabbitTemplate rabbitTemplate
     * @param millisecond 延迟毫秒
     * @param messageContent 发送字符串
     * @param busiId 业务主键id
     */
    public static void sendDelayMessage(RabbitTemplate rabbitTemplate, Integer millisecond, Object messageContent, Long busiId) {
        CorrelationData correlationData = new CorrelationData(busiId.toString() + System.currentTimeMillis());
        rabbitTemplate.convertAndSend("testQueue", "", messageContent,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    message.getMessageProperties().setDelay(millisecond);
                    return message;
                }, correlationData);
    }
}
