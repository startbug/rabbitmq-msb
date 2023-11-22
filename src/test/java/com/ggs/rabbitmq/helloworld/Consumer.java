package com.ggs.rabbitmq.helloworld;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 10:41
 */
public class Consumer {

    @Test
    public void consume() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        // 不持久化，不排外(可以多个消费者监听一个队列)，不自动删除，无需参数
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        // 4.消费消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者接收到消息:" + new String(body, "UTF-8"));
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME, true, callback);
    }

}
