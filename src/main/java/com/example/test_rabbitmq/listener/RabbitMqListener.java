package com.example.test_rabbitmq.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author ：liujl
 * @date ：Created 2023/7/12 17:38
 * @description：
 */
@Component
@Slf4j
@RabbitListener(queues = "LAZY_QUEUE")
public class RabbitMqListener {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitHandler
    public void onCustomBookingMessage(@Payload String message, Channel channel, @Headers Map<String, Object> headers) {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            Map map = objectMapper.readValue(message, Map.class);
            log.info("接收消息成功，消息内容：{}", map);
            ack(channel, deliveryTag);
        } catch (JsonProcessingException e) {
            nack(channel, deliveryTag);
        }
    }

    /**
     * 确认消息成功
     */
    public void ack(Channel channel, long deliveryTag) {
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.error("消息应答出错", e);
        }
    }

    /**
     * 确认消息失败
     */
    public void nack(Channel channel, long deliveryTag) {
        try {
            channel.basicNack(deliveryTag, false, true);
        } catch (IOException e) {
            log.error("消息拒绝出错", e);
        }
    }
}
