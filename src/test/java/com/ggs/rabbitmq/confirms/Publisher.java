package com.ggs.rabbitmq.confirms;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 23:17
 * 1.保证消息一定送到Exchange
 * Confirm机制
 * <p>
 * 2.保证消息可以路由到Queue
 * Return机制
 */
public class Publisher {

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        channel.queueDeclare("confirms", true, false, false, null);

        // 4 5步骤用于确保消息能从生产者发到交换机
        // 4.开启confirms
        channel.confirmSelect();

        // 5.设置confirms的异步回调
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息成功发送到交换机");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息没有发送到交换机,尝试进行其他补救操作");
            }
        });

        // 6.设置return回调，确认消息是否路由到Queue，如果没有路由到Queue，则会调用return回调方法
        // 只有mandatory设置为true(发送消息时设置),才会开启return机制
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("消息没有路由到指定队列,做其他补偿措施!!!");
            }
        });

        // 7.设置消息持久化
        BasicProperties props = new BasicProperties()
                .builder()
                .deliveryMode(2)    // 2表示持久化
                .build();

        // 7.发布消息
        String message = "Hello world";
        channel.basicPublish("", "confirms", true, props, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送成功!!!");

        System.in.read();
    }

}
