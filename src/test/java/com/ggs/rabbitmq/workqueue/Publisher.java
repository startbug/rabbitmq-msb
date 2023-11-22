package com.ggs.rabbitmq.workqueue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.ggs.rabbitmq.util.MQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 13:14
 */
public class Publisher {

    public static final String QUEUE_NAME = "work_queue";

    @Test
    public void publish() throws Exception {
        //1.获取连接
        Connection connection = MQConnectionUtil.getConnection();

        //2.创建channel
        Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //4.发送消息
        String message = "queuework消息-";
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, (message + i).getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("发送成功");
    }

}
