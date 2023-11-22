package com.ggs.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.ggs.rabbitmq.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 22:15
 */
@Configuration
public class ConsumeListener {

    /**
     * @param message 消息内容
     * @param channel 只有开启了手动ack，才有值(spring.rabbitmq.listener.simple.acknowledge-mode: manual)
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(String content, Channel channel, Message message) throws IOException {
        System.out.println("队列的消息为:" + content);
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("唯一标识为:" + correlationId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
