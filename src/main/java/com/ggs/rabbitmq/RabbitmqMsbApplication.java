package com.ggs.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ggs.rabbitmq.demo.mapper")
public class RabbitmqMsbApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqMsbApplication.class, args);
    }

}
