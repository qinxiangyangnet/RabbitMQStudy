package com.yueyang.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:59
 **/
@SpringBootApplication
public class MsgConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsgConsumerApplication.class, args);
    }
}