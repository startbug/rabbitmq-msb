package com.ggs.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 10:14
 */
public class MQConnectionUtil {

    public static final String RABBITMQ_HOST = "8.134.177.106";

    public static final int RABBITMQ_PORT = 5672;

    public static final String RABBITMQ_USERNAME = "guest";

    public static final String RABBITMQ_PASSWORD = "guest";

    public static final String RABBITMQ_VIRTUAL_HOST = "/";

    /**
     * 获取RabbitMQ的链接对象
     */
    public static Connection getConnection() throws Exception {
        //1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //2.设置RabbitMQ的连接信息
        factory.setHost(RABBITMQ_HOST);
        factory.setPort(RABBITMQ_PORT);
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUAL_HOST);

        //3.返回连接对象
        Connection connection = factory.newConnection();

        return connection;
    }

}
