package com.example.test_rabbitmq.controller;

import com.example.test_rabbitmq.utils.RabbitMqUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：liujl
 * @date ：Created 2023/7/12 17:44
 * @description：
 */
@Slf4j
@RestController
@RequestMapping("/rabbitmq")
@RequiredArgsConstructor
public class RabbitMqController {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @GetMapping("/sendDelayMessage")
    public void sendDelayMessage() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "test");
        map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 发送消息 延迟2秒
        RabbitMqUtils.sendDelayMessage(rabbitTemplate, 2000, objectMapper.writeValueAsString(map), System.currentTimeMillis());
        log.info("发送延迟队列消息成功！消息内容：{}", map);
    }
}
