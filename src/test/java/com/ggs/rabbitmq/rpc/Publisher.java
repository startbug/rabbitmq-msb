package com.ggs.rabbitmq.rpc;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 17:03
 */
public class Publisher {

    public static final String PUBLISH_QUEUE = "publish_rpcqueue";

    public static final String CONSUMER_QUEUE = "consumer_rpcqueue";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare(PUBLISH_QUEUE, false, false, false, null);
        channel.queueDeclare(CONSUMER_QUEUE, false, false, false, null);

        // 4.发布消息
        String message = "Hello RPC!!!";
        String uuid = UUID.randomUUID().toString();
        BasicProperties props = new BasicProperties().builder()
                .correlationId(uuid)            // 标识
                .replyTo(CONSUMER_QUEUE)        // 告诉消费者，响应的信息发送到哪一个队列
                .build();
        channel.basicPublish("", PUBLISH_QUEUE, props, message.getBytes());

        channel.basicConsume(CONSUMER_QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                String id = properties.getCorrelationId();
                if (id != null && id.equals(uuid)) {
                    System.out.println("接收到的响应:" + new String(body, "UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });
        System.out.println("发送消息成功");
        System.in.read();
    }

}
