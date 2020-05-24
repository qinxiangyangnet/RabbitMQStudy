package com.yueyang.rabbitmq.email;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: queueproject
 * @description: email消息消费者
 * @author: qinxiangyang
 * @create: 2020-05-24 10:55
 **/
@Component
@RabbitListener(queues = "new_fanout_email_queue")
public class EmailConsumer {


    @RabbitHandler
    public void process(String msg) {

        System.out.println("邮件消费者获取生产者生成的消息:" + msg);

    }
}