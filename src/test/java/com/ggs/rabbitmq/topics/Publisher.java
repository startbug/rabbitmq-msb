package com.ggs.rabbitmq.topics;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 17:03
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "topic_exchange";

    public static final String QUEUE_NAME1 = "topic_queue_1";

    public static final String QUEUE_NAME2 = "topic_queue_2";

    @Test
    public void publish() throws Exception {
        // 1.获取连接对象
        Connection connection = MQConnectionUtil.getConnection();

        // 2.构建Channel
        Channel channel = connection.createChannel();

        // 3.构建交换机(fanout类型)
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 4.构建队列
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);

        // 5.绑定交换机和队列，使用fanout类型的交换机，绑定方式是直接绑定，无需指定routingKey
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "*.orange.*");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "lazy.#");

        // 6.发送消息到交换机
        channel.basicPublish(EXCHANGE_NAME, "big.orange.rabbit", null, "大橙几".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "small.white.rabbit", null, "小白兔".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "lazy.dog.dog.dog.dog.dog.dog", null, "蓝狗狗狗狗狗".getBytes());
        System.out.println("发送消息成功");
    }

}
