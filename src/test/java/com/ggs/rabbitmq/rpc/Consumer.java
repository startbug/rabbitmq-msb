package com.ggs.rabbitmq.rpc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.AMQP;
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
        channel.queueDeclare(Publisher.PUBLISH_QUEUE, false, false, false, null);
        channel.queueDeclare(Publisher.CONSUMER_QUEUE, false, false, false, null);

        // 4.消费消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者接收到消息:" + new String(body, "UTF-8"));
                String resp = "获取到client发出的请求，正在处理中...";
                String uuid = properties.getCorrelationId();    // 请求的标识
                String respQueueName = properties.getReplyTo();       // 响应的队列名
                BasicProperties props = new BasicProperties().builder()
                        .correlationId(uuid)
                        .build();
                channel.basicPublish("", respQueueName, props, resp.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.PUBLISH_QUEUE, false, callback);
        System.out.println("开始监听队列");
        System.in.read();
    }

}
