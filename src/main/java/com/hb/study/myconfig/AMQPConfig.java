package com.hb.study.myconfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangbing
 * @date 2020/04/10 17:22
 */
@Configuration
public class AMQPConfig {
    /** RabbitTemplate默认使用的是SimpleMessageConverter消息转化器,该转换器序列化时使用的是Jdk规则
     *  private MessageConverter messageConverter = new SimpleMessageConverter();
     *  自定义消息转换器,使用json。RabbitAutoConfiguration在配置RabbitTemplate时候会帮我自动设置进去
     */

//    @Bean
    public RabbitTemplate customRabbitTemplate(){
           RabbitTemplate rabbitTemplate = new RabbitTemplate();
           Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//           ObjectMapper objectMapper = new ObjectMapper();
           rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
           return rabbitTemplate;
    }
}
