package com.ggs.rabbitmq;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ggs.rabbitmq.config.RabbitMQConfig;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 22:08
 */
@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void publish() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "大黑狗");
        System.out.println("消息发送成功");
    }

    @Test
    public void publishWithProps() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "大黑狗WithProps", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId("123");
                return message;
            }
        });
        System.out.println("消息发送成功");
    }

    @Test
    public void publishWithConfirms() {
        // 首先开启Confirm机制--> spring.rabbitmq.publisher-confirm-type: correlated
        rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("消息已送达到交换机！！！");
                } else {
                    System.out.println("消息没有送达到Exchange，需要进行补偿");
                }
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "大黑狗");
        System.out.println("消息发送成功");
    }

    @Test
    public void publishWithReturn() throws IOException {
        // 开启Confirm机制,编写confirm回调方法--> spring.rabbitmq.publisher-confirm-type: correlated
        rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("消息已送达到交换机！！！");
                } else {
                    System.out.println("消息没有送达到Exchange，需要进行补偿");
                }
            }
        });

        // 开启Return机制,编写return回调方法--> spring.rabbitmq.publisher-returns: true
        rabbitTemplate.setReturnsCallback(new ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                String msg = new String(returned.getMessage().getBody());
                System.out.println("消息:" + msg + "路由队列失败,需要进行补偿");
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.white.dog", "大黑狗");
        System.out.println("消息发送成功");
        System.in.read();
    }

    @Test
    public void publishWithBasicProperties() {
        // 消息持久化
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "大黑狗", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        });
        System.out.println("消息发送成功");
    }

}
