package com.ggs.rabbitmq.listener;


import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ggs.rabbitmq.config.DeadLetterConfig;
import com.ggs.rabbitmq.config.DelayedConfig;
import com.rabbitmq.client.Channel;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/28 23:46
 */
@Component
public class DeadListener {

//    @RabbitListener(queues = DeadLetterConfig.NORMAL_QUEUE)
//    public void normalConsume(String msg, Channel channel, Message message) throws IOException {
//        System.out.println("接收到normal消息:" + msg);
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//    }

//    @RabbitListener(queues = DeadLetterConfig.DEAD_QUEUE)
//    public void deadConsume(String msg, Channel channel, Message message) throws IOException {
//        System.out.println("接收到dead消息:" + msg);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }

    @RabbitListener(queues = DelayedConfig.DELAYED_QUEUE)
    public void deadConsume(String msg, Channel channel, Message message) throws IOException {
        System.out.println("接收到dead消息:" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
