package com.ggs.rabbitmq.headers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Maps;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/12/3 18:20
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "headers_exchange";

    public static final String QUEUE_NAME = "headers_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = MQConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 构建交换机和队列并基于header的方式绑定
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        Map<String, Object> arguments = new HashMap<>();
        // x-match有两种选择:all和any
        // all:表示消息头中的key和value需要完全匹配，才会发送到队列中
        // any:表示消息头中的key必须要完全匹配，value匹配1个或以上，就会发送到队列中
        arguments.put("x-match", "any");
        arguments.put("name", "lhh");
        arguments.put("age", "24");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "", arguments);

        // 发送消息
        String msg = "header测试消息";
        Map<String, Object> headers = new HashMap<>();
        headers.put("nam1e", "l1hh");
        headers.put("age", "22");
        BasicProperties props = new BasicProperties().builder()
                .headers(headers)
                .build();
        channel.basicPublish(EXCHANGE_NAME, "", props, msg.getBytes(StandardCharsets.UTF_8));

        System.out.println("消息发送成功,header = " + headers);
    }

}
