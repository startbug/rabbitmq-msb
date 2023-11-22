package com.ggs.rabbitmq.helloworld;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 10:34
 */
public class Publisher {

    public static final String QUEUE_NAME = "hello";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        // 不持久化，不排外(可以多个消费者监听一个队列)，不自动删除，无需参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 4.发布消息
        // exchange: 空字符串，表示发送到默认交换机中
        // routingKey: 路由key，使用默认交换机，会将消息路由到同名的队列中，所以routingKey直接用队列名即可
        String message = "Hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送成功!!!");
    }

}
