package com.example.test_rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：liujl
 * @date ：Created 2023/7/12 17:33
 * @description：
 */
@Configuration
public class RabbitMqConfig {

    @Bean(name = "testQueue")
    public TopicExchange lazyExchange() {
        Map<String, Object> pros = new HashMap<>();
        //设置交换机支持延迟消息推送
        pros.put("x-delayed-type", "direct");
        TopicExchange exchange = new TopicExchange("testQueue", true, false, pros);
        // 这一行是重点，指定交换机类型
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Queue lazyQueue() {
        return new Queue("LAZY_QUEUE", true);
    }

    @Bean
    public Binding lazyBinding() {
        return BindingBuilder.bind(lazyQueue()).to(lazyExchange()).with("");
    }
}
