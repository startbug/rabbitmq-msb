package com.ggs.rabbitmq.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    public void consume1() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        // 不持久化，不排外(可以多个消费者监听一个队列)，不自动删除，无需参数
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        // 3.5设置消息的流控(流量控制),设置之后，才做到能者多劳，谁先消费完，谁就继续从队列中拿消息继续消费，如果不设置，不管消费者效率如何，队列的消息就会直接分派完
        // 每次拿多少条消息进行消费(消费完之后会从队列中继续获取消息消费)
        channel.basicQos(3);

        // 4.消费消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费者11111接收到消息:" + new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME, false, callback);
        System.out.println("111开始监听");
        System.in.read();
    }

    @Test
    public void consume2() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建队列
        // 不持久化，不排外(可以多个消费者监听一个队列)，不自动删除，无需参数
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        // 3.5设置消息的流控(流量控制),设置之后，才做到能者多劳，谁先消费完，谁就继续从队列中拿消息继续消费，如果不设置，不管消费者效率如何，队列的消息就会直接分派完
        // 每次拿多少条消息进行消费(消费完之后会从队列中继续获取消息消费)
        channel.basicQos(3);

        // 4.消费消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费者22222接收到消息:" + new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME, false, callback);
        System.out.println("222开始监听");
        System.in.read();
    }

}
